package com.nowiwr01p.domain.meetings.data

import kotlinx.serialization.Serializable

interface OrderedItem {
    val order: Int
}

@Serializable
data class Story(
    val id: String = "",
    val priority: Int = 0,
    val previewIcon: String = "",
    val previewTitle: String = "",
    val title: List<Title> = listOf(),
    val description: List<Description> = listOf(),
    val orderedList: List<OrderedList> = listOf(),
    val viewed: Boolean = false,
    val viewers: List<String> = listOf()
) {
    fun getContent() = (title + description + orderedList).sortedBy { it.order }
}

@Serializable
data class Title(
    override val order: Int = 0,
    val text: String = ""
): OrderedItem

@Serializable
data class Description(
    override val order: Int = 0,
    val text: String = ""
): OrderedItem

@Serializable
data class OrderedList(
    override val order: Int = 0,
    val title: String = "",
    val steps: List<String> = listOf()
): OrderedItem