package com.nowiwr01p.meetings.ui

import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface MeetingsContract {

    sealed interface Event: ViewEvent {
        object Init: Event
    }

    class State: ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {

    }
}