package com.nowiwr01p.domain.meetings.main.repository

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.app.ReferencedListener
import kotlinx.coroutines.flow.StateFlow

interface MeetingsRepository {
    suspend fun subscribeMeetings(): ReferencedListener
    suspend fun getMeetings(): StateFlow<List<Meeting>>
    suspend fun getUnpublishedMeetings(): List<Meeting>
}