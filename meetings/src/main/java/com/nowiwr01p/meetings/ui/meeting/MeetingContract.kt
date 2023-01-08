package com.nowiwr01p.meetings.ui.meeting

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface MeetingContract {

    sealed interface Event: ViewEvent {
        object Init: Event
    }

    class State(
        val meeting: Meeting = Meeting.getSampleData()
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {

    }
}