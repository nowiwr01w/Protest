package com.nowiwr01p.core.model

import com.nowiwr01p.core.model.ArticleContentType.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentItem(
    @SerialName("order")
    val order: Int = 0,
    @SerialName("type")
    val type: String = "",
    @SerialName("value")
    val value: String = ""
) {
    val articleType: ArticleContentType
        get() = ArticleContentType.findByType(type)
}

@Serializable
data class Article(
    @SerialName("date")
    val date: Long = 0,
    @SerialName("content")
    val content: List<ContentItem> = listOf()
) {
    fun getField(type: ArticleContentType) = content.find { it.articleType == type }?.value ?: ""

    companion object {
        val article = Article(
            date = 1673103472373,
            content = listOf(
                ContentItem(
                    order = 1,
                    type = "title",
                    value = "test title"
                ),
                ContentItem(
                    order = 2,
                    type = "image",
                    value = "https://www.rd.com/wp-content/uploads/2021/01/GettyImages-1175550351.jpg"
                ),
                ContentItem(
                    order = 3,
                    type = "description",
                    value = "description of the article"
                ),
            )
        )
    }
}