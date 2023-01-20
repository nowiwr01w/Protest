package com.nowiwr01p.profile.ui

import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface ProfileContract {

    sealed interface Event: ViewEvent {

    }

    class State: ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {

    }
}