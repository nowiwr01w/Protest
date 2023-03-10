package com.nowiwr01p.news.ui.news

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core.model.DateViewers
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface NewsContract {

    sealed interface Event : ViewEvent {
        object Init: Event
        object NavigateToCreateArticle: Event
        data class OnArticleClick(val article: Article): Event
    }

    data class State(
        val user: User = User(),
        val isLoading: Boolean = true,
        val newsList: List<Article> = listOf(),
        val newsViewers: Map<String, DateViewers> = mapOf()
    ) : ViewState

    sealed interface Effect : ViewSideEffect {
        object NavigateToCreateArticle: Effect
        data class ShowArticle(val article: Article): Effect
    }

    interface Listener {
        fun onBackClick()
        fun toUnpublishedNews()
        fun onArticleClick(article: Article)
        fun toCreateArticle()
    }
}