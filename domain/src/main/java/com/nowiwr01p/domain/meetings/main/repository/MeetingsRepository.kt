package com.nowiwr01p.domain.meetings.main.repository

import com.nowiwr01p.core.datastore.cities.data.Meeting

interface MeetingsRepository {
    suspend fun getMeetings(): List<Meeting>
    suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean): Meeting
    suspend fun createMeeting(meeting: Meeting)
}