package com.nowiwr01p.meetings.ui.map_current_meeting

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.core.datastore.cities.data.MeetingStatus
import com.nowiwr01p.core.datastore.cities.data.MeetingStatus.*
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.ui.button.ButtonState
import com.nowiwr01p.core_ui.ui.button.ButtonState.*

interface MapCurrentMeetingContract {

    sealed interface Event: ViewEvent {
        data class Init(val meeting: Meeting): Event
        object RunMeeting: Event
    }

    data class State(
        val user: User = User(),
        val meeting: Meeting = Meeting(),
        val meetingStatus: MeetingStatus = WAITING_FOR_PEOPLE,
        val title: String = "Текущий митинг",
        val runMeetingButtonState: ButtonState = DEFAULT
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateBack: Effect
    }

    interface Listener {
        fun onBackClick()
        fun runMeeting()
    }
}