package com.nowiwr01p.news.ui.article

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.ui.button.ButtonState
import com.nowiwr01p.core_ui.ui.button.ButtonState.*
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface ArticleContract {

    sealed interface Event : ViewEvent {
        data class Init(val article: Article) : Event
        object PublishArticle: Event
    }

    data class State(
        val article: Article = Article(),
        val publishButtonState: ButtonState = DEFAULT,
        val viewsCount: Int = 0
    ) : ViewState

    sealed interface Effect : ViewSideEffect

    interface Listener {
        fun onBackClick()
        fun onPublishArticle()
    }
}