package com.nowiwr01p.domain.meetings.meeting.repository

import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.core.datastore.cities.data.Meeting
import kotlinx.coroutines.flow.StateFlow

interface MeetingRepository {
    suspend fun subscribeMeeting(id: String): StateFlow<Meeting>
    suspend fun getMeetingLocation(meetingId: String): LocationInfo
    suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean): Meeting
    suspend fun runMeeting(location: LocationInfo)
}