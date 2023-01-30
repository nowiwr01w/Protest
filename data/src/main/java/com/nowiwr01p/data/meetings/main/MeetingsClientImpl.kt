package com.nowiwr01p.data.meetings.main

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.datastore.cities.data.MeetingDB
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.main.repository.MeetingsClient
import com.nowiwr01p.domain.user.client.UserClient
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

class MeetingsClientImpl(
    private val references: FirebaseReferencesRepository,
    private val userClient: UserClient,
    private val dispatchers: AppDispatchers
): MeetingsClient {

    private val meetingsFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(listOf())

    /**
     * MEETINGS
     */
    override suspend fun getMeetings() = meetingsFlow

    override suspend fun subscribeMeetings(): Unit = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, Meeting>> { map ->
            val updated = map.values.sortedByDescending { meeting -> meeting.date }
            CoroutineScope(dispatchers.io).launch {
                meetingsFlow.emit(updated)
            }
        }
        references.getMeetingsReference().addValueEventListener(listener)
    }

    /**
     * MEETING LOCATION
     */
    override suspend fun getMeetingLocation(meetingId: String) = withContext(dispatchers.io) {
        references.getLocationsReference(meetingId).get()
            .await()
            .getValue<LocationInfo>()
    }

    /**
     * CREATE MEETING
     */
    override suspend fun createMeeting(meeting: Meeting): Unit = withContext(dispatchers.io) {
        val sendMeeting = async {
            references.getMeetingReference(meeting.id)
                .setValue(MeetingDB.toMeetingDB(meeting))
                .await()
        }
        val sendLocation = async {
            references.getLocationsReference(meeting.id)
                .setValue(meeting.locationInfo)
                .await()
        }
        listOf(sendMeeting, sendLocation).awaitAll()
    }

    /**
     * REACTION
     */
    override suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean) = withContext(dispatchers.io) {
        val updatedMeeting = references.getMeetingReference(meetingId).get().await()
            .getValue<Meeting>()!!
            .updateMeeting(isPositiveButtonClicked)
        references.getMeetingReference(meetingId).setValue(updatedMeeting).await()
        updatedMeeting
    }

    private suspend fun Meeting.updateMeeting(positive: Boolean): Meeting {
        val userId = userClient.getUserFlow().value.id

        val positiveContains = reaction.peopleGoCount.contains(userId)
        val maybeContains = reaction.peopleMaybeGoCount.contains(userId)

        val positiveReactionsFiltered = reaction.peopleGoCount
            .filterNot { userId == it }
            .toMutableList()
        val maybeReactionsFiltered = reaction.peopleMaybeGoCount
            .filterNot { userId == it }
            .toMutableList()

        if (positive && !positiveContains) positiveReactionsFiltered.add(userId)
        if (!positive && !maybeContains) maybeReactionsFiltered.add(userId)

        val updatedReaction = reaction.copy(
            peopleGoCount = positiveReactionsFiltered,
            peopleMaybeGoCount = maybeReactionsFiltered
        )
        return copy(reaction = updatedReaction)
    }
}