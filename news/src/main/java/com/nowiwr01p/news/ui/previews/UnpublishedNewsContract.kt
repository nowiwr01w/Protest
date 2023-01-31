package com.nowiwr01p.news.ui.previews

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface UnpublishedNewsContract {

    sealed interface Event: ViewEvent {
        object Init: Event
    }

    data class State(
        val loaded: Boolean = false,
        val news: List<Article> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {
        fun onBackClick()
        fun toArticle(article: Article)
    }
}