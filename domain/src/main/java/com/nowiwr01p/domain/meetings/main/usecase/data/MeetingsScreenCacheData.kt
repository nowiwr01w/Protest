package com.nowiwr01p.domain.meetings.main.usecase.data

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.meetings.main.data.Story

data class MeetingsScreenCacheData(
    val user: User = User(),
    val stories: List<Story> = listOf(),
    val categories: List<Category> = listOf(),
    val meetings: List<Meeting> = listOf()
)