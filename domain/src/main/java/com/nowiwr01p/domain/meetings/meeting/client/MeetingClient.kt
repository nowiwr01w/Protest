package com.nowiwr01p.domain.meetings.meeting.client

import com.nowiwr01p.core.datastore.cities.data.Meeting
import kotlinx.coroutines.flow.StateFlow

interface MeetingClient {
    suspend fun getMeetingFlow(): StateFlow<Meeting>
    suspend fun subscribeMeeting(id: String)
}