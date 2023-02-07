package com.nowiwr01p.domain.meetings.meeting.client

import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.core.datastore.cities.data.Meeting
import kotlinx.coroutines.flow.StateFlow

interface MeetingClient {
    suspend fun subscribeMeeting(id: String): StateFlow<Meeting>
    suspend fun runMeeting(location: LocationInfo)
}