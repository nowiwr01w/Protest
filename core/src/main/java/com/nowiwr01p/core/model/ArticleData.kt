package com.nowiwr01p.core.model

import kotlinx.serialization.Serializable

abstract class ArticleData {

    abstract var order: Int // used to show preview & articles from back-end

    fun setItemOrder(index: Int) = apply { order = index }
}

@Serializable
data class TopImage(
    override var order: Int = 0,
    val link: String = ""
): ArticleData()

@Serializable
data class DateViewers(
    override var order: Int = 0,
    val date: Long = 0L,
    val viewers: List<String> = listOf()
): ArticleData()

@Serializable
data class Title(
    override var order: Int = 0,
    val text: String = ""
): ArticleData()

@Serializable
data class SubTitle(
    override var order: Int = 0,
    val text: String = ""
): ArticleData()

@Serializable
data class Description(
    override var order: Int = 0,
    val text: String = ""
): ArticleData()

@Serializable
data class Text(
    override var order: Int = 0,
    val text: String = ""
): ArticleData()

@Serializable
data class Quote(
    override var order: Int = 0,
    val text: String = ""
): ArticleData()

@Serializable
data class Image(
    val link: String = "",
    val description: String = ""
)

@Serializable
data class ImageList(
    override var order: Int = 0,
    val images: List<Image> = listOf(Image())
): ArticleData()

@Serializable
data class OrderedList(
    override var order: Int = 0,
    val title: String = "",
    val steps: List<String> = listOf("")
): ArticleData()