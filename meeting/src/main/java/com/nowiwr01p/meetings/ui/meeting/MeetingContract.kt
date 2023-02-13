package com.nowiwr01p.meetings.ui.meeting

import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.ui.button.ButtonState
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode
import com.nowiwr01p.meetings.extensions.getCityCoordinates
import com.nowiwr01p.meetings.extensions.getMeetingCoordinates

interface MeetingContract {

    sealed interface Event: ViewEvent {
        data class CreateMeeting(val mode: CreateMeetingMode): Event
        data class Init(val meeting: Meeting): Event
        data class OpenLink(val link: String): Event
        data class SetReaction(val isPositiveButtonClicked: Boolean): Event
    }

    data class State(
        val loaded: Boolean = false,
        val user: User = User(),
        val meeting: Meeting = Meeting.getSampleData(),
        val createMeetingButtonState: ButtonState = ButtonState.DEFAULT
    ): ViewState {
        val cityCoordinates: LatLng
            get() = user.getCityCoordinates()
        val meetingCoordinates: LatLng
            get() = meeting.getMeetingCoordinates()
    }

    sealed interface Effect: ViewSideEffect {
        object OnCreateMeetingSuccess: Effect
    }

    interface Listener {
        fun onBack()
        fun openLink(link: String)
        fun createMeeting(mode: CreateMeetingMode)
        fun setReaction(isPositiveButtonClicked: Boolean)
    }
}