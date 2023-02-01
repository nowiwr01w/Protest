package com.nowiwr01p.meetings.ui.map_current_meeting

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.meetings.ui.map_current_meeting.MapCurrentMeetingContract.*

class MapCurrentMeetingViewModel: BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meetingId)
        }
    }

    private fun init(meetingId: String) {

    }
}