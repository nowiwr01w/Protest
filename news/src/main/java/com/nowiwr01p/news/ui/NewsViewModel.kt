package com.nowiwr01p.news.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.news.ui.NewsContract.*

class NewsViewModel : BaseViewModel<Event, State, Effect>() {
    override fun setInitialState(): State = State()

    override fun handleEvents(event: Event) {
        TODO("Not yet implemented")
    }
}