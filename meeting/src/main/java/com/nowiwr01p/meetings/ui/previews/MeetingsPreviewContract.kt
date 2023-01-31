package com.nowiwr01p.meetings.ui.previews

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface MeetingsPreviewContract {

    sealed interface Event: ViewEvent {
        object Init: Event
    }

    data class State(
        val loaded: Boolean = false,
        val meetings: List<Meeting> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect

    interface Listener {
        fun onBackClick()
        fun toPublishMeeting(meeting: Meeting)
    }
}