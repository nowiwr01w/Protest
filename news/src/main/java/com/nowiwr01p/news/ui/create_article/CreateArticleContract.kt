package com.nowiwr01p.news.ui.create_article

import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface CreateArticleContract {

    sealed interface Event: ViewEvent {

    }

    class State: ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {

    }
}