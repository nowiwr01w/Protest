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
    val titles: List<Title> = listOf(),
    val images: List<Image> = listOf(),
    val descriptions: List<Description> = listOf(),
    val orderedLists: List<OrderedList> = listOf(),
    val viewers: List<String> = listOf()
)

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
data class Image(
    override val order: Int = 0,
    val link: String = "",
    val description: String = ""
): OrderedItem

@Serializable
data class OrderedList(
    override val order: Int = 0,
    val title: String = "",
    val steps: List<String> = listOf()
): OrderedItem