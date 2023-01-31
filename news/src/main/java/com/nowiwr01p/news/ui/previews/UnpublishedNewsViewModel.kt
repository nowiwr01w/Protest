package com.nowiwr01p.news.ui.previews

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.news.previews.GetUnpublishedNewsUseCase
import com.nowiwr01p.news.ui.previews.UnpublishedNewsContract.*

class UnpublishedNewsViewModel(
    private val getUnpublishedNewsUseCase: GetUnpublishedNewsUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
        }
    }

    private fun init() = io {
        runCatching {
            getUnpublishedNewsUseCase.execute()
        }.onSuccess {
            setState { copy(news = it, loaded = true) }
        }
    }
}