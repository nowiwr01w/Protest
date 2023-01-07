package com.nowiwr01p.meetings.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.usecase.GetCategoriesUseCase
import com.nowiwr01p.meetings.ui.MeetingsContract.*

class MeetingsViewModel(
    private val getCategories: GetCategoriesUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
        }
    }

    private fun init() = io {
        setState { copy(showShimmer = true) }
        runCatching {
            getCategories()
        }.onSuccess {
            setState { copy(showShimmer = false) }
        }
    }

    private suspend fun getCategories() {
        val categories = getCategories.execute()
        setState { copy(categories = categories) }
    }
}