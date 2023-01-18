package com.nowiwr01p.core.model

import kotlinx.serialization.Serializable

sealed interface ArticleData {
    val order: Int
}

@Serializable
data class TopImage(
    override val order: Int = 0,
    val link: String = ""
): ArticleData

@Serializable
data class DateViewers(
    override val order: Int = 0,
    val date: Long = 0L,
    val viewers: List<String> = listOf()
): ArticleData

@Serializable
data class Title(
    override val order: Int = 0,
    val text: String = ""
): ArticleData

@Serializable
data class SubTitle(
    override val order: Int = 0,
    val text: String = ""
): ArticleData

@Serializable
data class Description(
    override val order: Int = 0,
    val text: String = ""
): ArticleData

@Serializable
data class Text(
    override val order: Int = 0,
    val text: String = ""
): ArticleData

@Serializable
data class Image(
    val link: String = "",
    val description: String = ""
)

@Serializable
data class ImageList(
    override val order: Int = 0,
    val images: List<Image> = listOf(Image())
): ArticleData

@Serializable
data class OrderedList(
    override val order: Int = 0,
    val title: String = "",
    val steps: List<String> = listOf("")
): ArticleData