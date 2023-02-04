package com.nowiwr01p.domain.meetings.main.repository

import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode
import kotlinx.coroutines.flow.StateFlow

interface MeetingsClient {
    suspend fun subscribeMeetings()
    suspend fun getMeetings(): StateFlow<List<Meeting>>
    suspend fun getUnpublishedMeetings(): List<Meeting>
    suspend fun getMeetingLocation(meetingId: String): LocationInfo
    suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean): Meeting
    suspend fun createMeeting(mode: CreateMeetingMode, meeting: Meeting)
}