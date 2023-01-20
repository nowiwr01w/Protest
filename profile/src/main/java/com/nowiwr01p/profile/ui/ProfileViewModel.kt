package com.nowiwr01p.profile.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.profile.ui.ProfileContract.*

class ProfileViewModel: BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {

    }
}