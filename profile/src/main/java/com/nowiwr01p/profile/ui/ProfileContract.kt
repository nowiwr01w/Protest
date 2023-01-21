package com.nowiwr01p.profile.ui

import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface ProfileContract {

    sealed interface Event: ViewEvent {
        object Init: Event
    }

    data class State(
        val user: User = User()
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {

    }
}