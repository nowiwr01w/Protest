package com.nowiwr01p.meetings.ui.map_current_meeting

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.meetings.ui.map_current_meeting.MapCurrentMeetingContract.*

class MapCurrentMeetingViewModel: BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meeting)
        }
    }

    private fun init(meeting: Meeting) {
        setState { copy(meeting = meeting) }
    }
}