package com.nowiwr01p.core.model

import kotlinx.serialization.Serializable

@Serializable
sealed class ArticleData(val order: Int) {

    @Serializable
    data class TopImage(
        val priority: Int = 0,
        val link: String = ""
    ): ArticleData(priority)

    @Serializable
    data class DateViewers(
        val priority: Int = 0,
        val date: Long = 0L,
        val viewers: List<String> = listOf()
    ): ArticleData(priority)

    @Serializable
    data class Title(
        val priority: Int = 0,
        val text: String = ""
    ): ArticleData(priority)

    @Serializable
    data class Description(
        val priority: Int = 0,
        val text: String = ""
    ): ArticleData(priority)

    @Serializable
    data class Image(
        val link: String = "",
        val description: String = ""
    )

    @Serializable
    data class ImageList(
        val priority: Int = 0,
        val images: List<Image> = listOf()
    ): ArticleData(priority)

    @Serializable
    data class OrderedList(
        val priority: Int = 0,
        val title: String = "",
        val steps: List<String> = listOf()
    ): ArticleData(priority)
}