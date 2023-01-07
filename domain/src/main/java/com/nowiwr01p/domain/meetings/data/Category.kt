package com.nowiwr01p.domain.meetings.data

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String = "Политика",
    val priority: Int = 0,
    val isSelected: Boolean = false
)