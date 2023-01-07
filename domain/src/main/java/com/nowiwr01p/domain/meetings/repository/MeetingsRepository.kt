package com.nowiwr01p.domain.meetings.repository

import com.nowiwr01p.domain.meetings.data.Category

interface MeetingsRepository {
    suspend fun getCategories(): List<Category>
}