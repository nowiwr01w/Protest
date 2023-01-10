package com.nowiwr01p.meetings.ui.meeting

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface MeetingContract {

    sealed interface Event: ViewEvent {
        data class Init(val meeting: Meeting): Event
        data class OpenLink(val link: String): Event
    }

    data class State(
        val city: City = City(),
        val meeting: Meeting = Meeting.getSampleData()
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {
        fun onBack()
        fun openLink(link: String)
    }
}