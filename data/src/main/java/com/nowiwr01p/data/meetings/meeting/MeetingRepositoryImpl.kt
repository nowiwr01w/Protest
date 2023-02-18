package com.nowiwr01p.data.meetings.meeting

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.meeting.repository.MeetingRepository
import com.nowiwr01p.core.datastore.cities.data.MeetingStatus.IN_PROGRESS
import com.nowiwr01p.core.datastore.cities.data.Reaction
import com.nowiwr01p.domain.user.repository.UserRemoteRealtimeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MeetingRepositoryImpl(
    private val userRemoteRealtimeRepository: UserRemoteRealtimeRepository,
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): MeetingRepository {

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
    override suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean) {
        withContext(dispatchers.io) {
            val reaction = references.getMeetingReactionReference().child(meetingId)
                .get()
                .await()
                .getValue<Reaction>()!!
                .updateMeeting(isPositiveButtonClicked)
            references.getMeetingReactionReference().child(meetingId)
                .setValue(reaction)
                .await()
        }
    }

    private suspend fun Reaction.updateMeeting(positive: Boolean): Reaction {
        val userId = userRemoteRealtimeRepository.getUserFlow().value.id

        val positiveContains = peopleGoCount.contains(userId)
        val maybeContains = peopleMaybeGoCount.contains(userId)

        val positiveReactionsFiltered = peopleGoCount
            .filterNot { userId == it }
            .toMutableList()
        val maybeReactionsFiltered = peopleMaybeGoCount
            .filterNot { userId == it }
            .toMutableList()

        if (positive && !positiveContains) positiveReactionsFiltered.add(userId)
        if (!positive && !maybeContains) maybeReactionsFiltered.add(userId)

        return copy(
            peopleGoCount = positiveReactionsFiltered,
            peopleMaybeGoCount = maybeReactionsFiltered
        )
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