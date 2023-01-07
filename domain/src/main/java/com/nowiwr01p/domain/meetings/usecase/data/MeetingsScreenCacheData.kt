package com.nowiwr01p.domain.meetings.usecase.data

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.meetings.data.Category

data class MeetingsScreenCacheData(
    val user: User = User(),
    val categories: List<Category> = listOf(),
    val meetings: List<Meeting> = listOf()
)