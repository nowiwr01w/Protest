package com.nowiwr01p.news.ui

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface NewsContract {
    sealed interface Event: ViewEvent {
        object Init: Event
    }

    data class State(
        val isLoading: Boolean = true,
        val newsList: List<Article> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {
        fun onBackClick()
    }
}