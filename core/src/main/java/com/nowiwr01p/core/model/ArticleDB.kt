package com.nowiwr01p.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDB(
    val id: String = "",
    val topImage: TopImage = TopImage(),
    val title: Title = Title(),
    val description: Description = Description(),
    val text: List<Text> = listOf(),
    val quotes: List<Quote> = listOf(),
    val subtitles: List<SubTitle> = listOf(),
    val imagesLists: List<ImageList> = listOf(),
    val orderedLists: List<OrderedList> = listOf()
) {
    companion object {
        fun toArticleDB(article: Article) = ArticleDB(
            id = article.id,
            topImage = article.topImage,
            title = article.title,
            description = article.description,
            text = article.text,
            quotes = article.quotes,
            subtitles = article.subtitles,
            imagesLists = article.imagesLists,
            orderedLists = article.orderedLists
        )
    }
}