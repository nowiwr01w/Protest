package com.nowiwr01p.domain.meetings.main.repository

import com.nowiwr01p.core.datastore.cities.data.Meeting
import kotlinx.coroutines.flow.StateFlow

interface MeetingsClient {
    suspend fun subscribeMeetings()
    suspend fun getMeetings(): StateFlow<List<Meeting>>
    suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean): Meeting
    suspend fun createMeeting(meeting: Meeting)
}