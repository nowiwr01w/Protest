package com.nowiwr01p.meetings.ui

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.domain.meetings.data.Category

interface MeetingsContract {

    sealed interface Event: ViewEvent {
        object Init: Event
    }

    data class State(
        val showShimmer: Boolean = false,
        val meetings: List<Meeting> = listOf(),
        val categories: List<Category> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {

    }
}