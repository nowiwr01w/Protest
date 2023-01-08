package com.nowiwr01p.news.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.news.usecase.GetNewsUseCase
import com.nowiwr01p.news.ui.NewsContract.*

class NewsViewModel(
    private val getNews: GetNewsUseCase
) : BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
        }
    }

    private fun init() = io {
        runCatching {
            getNews.execute(Unit)
        }.onSuccess {
            setState { copy(newsList = it) }
        }
    }
}