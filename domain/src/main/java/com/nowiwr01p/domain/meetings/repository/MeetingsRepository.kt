package com.nowiwr01p.domain.meetings.repository

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.Category

interface MeetingsRepository {
    suspend fun getMeetings(): List<Meeting>
    suspend fun getCategories(): List<Category>
    suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean): Meeting
}