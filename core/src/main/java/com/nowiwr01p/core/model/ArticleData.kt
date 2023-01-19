package com.nowiwr01p.core.model

import kotlinx.serialization.Serializable

sealed interface ArticleData

@Serializable
data class TopImage(
    val link: String = ""
): ArticleData

@Serializable
data class DateViewers(
    val date: Long = 0L,
    val viewers: List<String> = listOf()
): ArticleData

@Serializable
data class Title(
    val text: String = ""
): ArticleData

@Serializable
data class SubTitle(
    val text: String = ""
): ArticleData

@Serializable
data class Description(
    val text: String = ""
): ArticleData

@Serializable
data class Text(
    val text: String = ""
): ArticleData

@Serializable
data class Image(
    val link: String = "",
    val description: String = ""
)

@Serializable
data class ImageList(
    val images: List<Image> = listOf(Image())
): ArticleData

@Serializable
data class OrderedList(
    val title: String = "",
    val steps: List<String> = listOf("")
): ArticleData