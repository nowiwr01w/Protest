package com.nowiwr01p.domain.meetings.create_meeting.repository

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode

interface CreateMeetingRepository {
    suspend fun createMeeting(mode: CreateMeetingMode, meeting: Meeting)
}