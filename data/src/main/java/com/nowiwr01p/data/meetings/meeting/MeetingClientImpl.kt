package com.nowiwr01p.data.meetings.meeting

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.meeting.client.MeetingClient
import com.nowiwr01p.core.datastore.cities.data.MeetingStatus.IN_PROGRESS
import com.nowiwr01p.domain.user.client.UserClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MeetingClientImpl(
    private val userClient: UserClient,
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): MeetingClient {

    private val meeting: MutableStateFlow<Meeting> = MutableStateFlow(Meeting())

    /**
     * SUBSCRIBE ON CURRENT MEETING
     */
    override suspend fun subscribeMeeting(id: String) = withContext(dispatchers.io) {
        val listener = createEventListener<Meeting> { curMeeting ->
            CoroutineScope(dispatchers.io).launch { meeting.emit(curMeeting) }
        }
        references.getMeetingReference(id).addValueEventListener(listener)
        meeting
    }

    /**
     * MEETING LOCATION
     */
    override suspend fun getMeetingLocation(meetingId: String) = withContext(dispatchers.io) {
        references.getLocationsReference(meetingId).get()
            .await()
            .getValue<LocationInfo>() ?: throw IllegalStateException("No location found")
    }

    /**
     * SET REACTION
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

    /**
     * CHANGE MEETING STATUS
     */
    override suspend fun runMeeting(location: LocationInfo): Unit = withContext(dispatchers.io) {
        val updated = meeting.value.copy(
            status = IN_PROGRESS,
            currentPosition = location.locationStartPoint
        )
        references.getMeetingReference(meeting.value.id).setValue(updated).await()
    }
}