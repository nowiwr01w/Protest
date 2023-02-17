package com.nowiwr01p.data.meetings.create_meeting

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.datastore.cities.data.MeetingDB
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.create_meeting.repository.CreateMeetingRepository
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CreateMeetingRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): CreateMeetingRepository {

    override suspend fun createMeeting(mode: CreateMeetingMode, meeting: Meeting): Unit = withContext(dispatchers.io) {
        val reference = when (mode) {
            SEND_TO_REVIEW -> references.getMeetingPreviewReference(meeting.id)
            PUBLISH_REVIEWED -> references.getMeetingReference(meeting.id)
        }
        val sendMeeting = async {
            reference
                .setValue(MeetingDB.toMeetingDB(meeting))
                .await()
        }
        val sendLocation = async {
            references.getLocationsReference(meeting.id)
                .setValue(meeting.locationInfo)
                .await()
        }
        listOf(sendMeeting, sendLocation).awaitAll().also {
            if (mode == PUBLISH_REVIEWED) {
                references.getMeetingPreviewReference(meeting.id).removeValue().await()
            }
        }
    }
}