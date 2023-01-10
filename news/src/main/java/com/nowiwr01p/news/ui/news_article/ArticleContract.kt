package com.nowiwr01p.news.ui.news_article

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface ArticleContract {

    sealed interface Event : ViewEvent {
        data class Init(val article: Article) : Event
        data class OpenLink(val link: String) : Event
    }

    data class State(
        val article: Article = Article(),
        val isLoading: Boolean = true
    ) : ViewState

    sealed interface Effect : ViewSideEffect {

    }

    interface Listener {
        fun onBackClick()
        fun onLinkClick(link: String)
    }
}