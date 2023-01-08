package com.nowiwr01p.meetings.ui.meeting

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.*

class MeetingViewModel: BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {

    }
}