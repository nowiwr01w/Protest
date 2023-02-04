package com.nowiwr01p.news.ui.create_article

import com.nowiwr01p.core.model.*
import com.nowiwr01p.core_ui.mapper.ViewModelMapper
import java.util.*

class CreateArticleMapper: ViewModelMapper<CreateArticleViewModel>() {

    fun getArticle() = with(viewModel.viewState.value) {
        val orderedContent = content.toMutableList().mapIndexed { index, articleData ->
            articleData.setItemOrder(index)
        }
        val dateViewers = DateViewers(
            date = System.currentTimeMillis(),
            viewers = listOf(userId)
        )
        with(orderedContent) {
            Article(
                id = UUID.randomUUID().toString(),
                topImage = filterIsInstance<TopImage>().first(),
                dateViewers = dateViewers,
                title = filterIsInstance<Title>().first(),
                description = filterIsInstance<Description>().first(),
                text = filterIsInstance<Text>(),
                quotes = filterIsInstance<Quote>(),
                subtitles = filterIsInstance<SubTitle>(),
                imagesLists = filterIsInstance<ImageList>(),
                orderedLists = filterIsInstance<OrderedList>(),
            )
        }
    }
}