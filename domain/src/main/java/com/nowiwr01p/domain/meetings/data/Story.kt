package com.nowiwr01p.domain.meetings.data

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val id: String = "",
    val priority: Int = 0,
    val previewIcon: String = "",
    val previewTitle: String = "",
    val title: String = "",
    val description: String = "",
    val orderedList: OrderedList = OrderedList(),
    val viewed: Boolean = false,
    val viewers: List<String> = listOf()
)

@Serializable
data class OrderedList(
    val title: String = "",
    val steps: List<String> = listOf()
)