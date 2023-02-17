package com.nowiwr01p.domain.meetings.main.repository

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.app.EventListener
import kotlinx.coroutines.flow.StateFlow

interface MeetingsClient {
    suspend fun subscribeMeetings(): EventListener
    suspend fun getMeetings(): StateFlow<List<Meeting>>
    suspend fun getUnpublishedMeetings(): List<Meeting>
}