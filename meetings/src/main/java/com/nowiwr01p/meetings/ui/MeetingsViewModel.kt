package com.nowiwr01p.meetings.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.meetings.ui.MeetingsContract.*

class MeetingsViewModel: BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {

    }
}