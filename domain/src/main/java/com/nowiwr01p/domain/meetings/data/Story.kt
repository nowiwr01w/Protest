package com.nowiwr01p.domain.meetings.data

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val priority: Int = 0,
    val previewIcon: String = "",
    val previewTitle: String = "",
    val title: String = "",
    val description: String = "",
    val orderedList: OrderedList = OrderedList()
)

@Serializable
data class OrderedList(
    val title: String = "",
    val steps: List<String> = listOf()
)