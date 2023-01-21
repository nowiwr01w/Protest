package com.nowiwr01p.profile.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.profile.ui.ProfileContract.*

class ProfileViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
        }
    }

    private fun init() = io {
        runCatching {
            getUser()
        }
    }

    private suspend fun getUser() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }
}