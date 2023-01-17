package com.nowiwr01p.news.ui.create_article

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*

class CreateArticleViewModel: BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.NavigateBack -> setEffect { Effect.NavigateBack }
        }
    }
}