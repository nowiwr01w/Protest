package com.nowiwr01p.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("date")
    val date: Long,
    @SerialName("news_list")
    val components: List<ArticleComponent>
)  {
    companion object DataForPreview {
        val article = Article (
            date = 1673103472373,
            components = listOf(
                ArticleComponent (
                    order = 1,
                    type = "title",
                    value = "test title"
                ),
                ArticleComponent (
                    order = 2,
                    type = "image",
                    value = "https://www.rd.com/wp-content/uploads/2021/01/GettyImages-1175550351.jpg"
                ),
                ArticleComponent (
                    order = 3,
                    type = "description",
                    value = "description of the article"
                ),
            )
        )
    }
}

@Serializable
data class ArticleComponent(
    @SerialName("order")
    val order: Int,
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String
)