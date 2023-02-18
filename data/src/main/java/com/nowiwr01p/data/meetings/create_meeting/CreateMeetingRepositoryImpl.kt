package com.nowiwr01p.data.meetings.create_meeting

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.datastore.cities.data.MeetingDB
import com.nowiwr01p.core.datastore.cities.data.Reaction
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.create_meeting.repository.CreateMeetingRepository
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode.*
import com.nowiwr01p.domain.user.repository.UserRemoteRealtimeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CreateMeetingRepositoryImpl(
    private val repository: UserRemoteRealtimeRepository,
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): CreateMeetingRepository {

    override suspend fun createMeeting(mode: CreateMeetingMode, meeting: Meeting) {
        when (mode) {
            SEND_TO_REVIEW -> sendMeetingReview(meeting)
            PUBLISH_REVIEWED -> publishReviewed(meeting)
        }
    }

    /**
     * PUBLISH REVIEWED MEETING
     */
    private suspend fun publishReviewed(meeting: Meeting) = withContext(dispatchers.io) {
        val sendMeeting = async {
            references.getMeetingReference(meeting.id)
                .setValue(MeetingDB.toMeetingDB(meeting))
                .await()
        }
        val removeFromPreview = async {
            references.getMeetingPreviewReference(meeting.id)
                .removeValue()
                .await()
        }
        listOf(sendMeeting, removeFromPreview).awaitAll()
    }

    /**
     * SEND MEETING FOR REVIEW
     */
    private suspend fun sendMeetingReview(meeting: Meeting) = withContext(dispatchers.io) {
        val sendMeeting = async {
            references.getMeetingPreviewReference(meeting.id)
                .setValue(MeetingDB.toMeetingDB(meeting))
                .await()
        }
        val sendLocation = async {
            references.getLocationsReference(meeting.id)
                .setValue(meeting.locationInfo)
                .await()
        }
        val sendReaction = async {
            val userId = repository.getUserFlow().value.id
            val peopleGo = listOf(userId)
            references.getMeetingReactionReference().child(meeting.id)
                .setValue(Reaction(peopleGoCount = peopleGo))
                .await()
        }
        listOf(sendMeeting, sendLocation, sendReaction).awaitAll()
    }
}