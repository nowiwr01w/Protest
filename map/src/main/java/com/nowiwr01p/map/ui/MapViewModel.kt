package com.nowiwr01p.map.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.meetings.usecase.GetCachedMeetingsUseCase
import com.nowiwr01p.map.ui.MapContract.*

class MapViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val getCachedMeetingsUseCase: GetCachedMeetingsUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.OnBackClick -> back()
        }
    }

    private fun init() = io {
        changeStatusBarState(true)
        runCatching {
            getLocalUser()
            getCachedMeetings()
        }.onSuccess {
            setState { copy(showProgress = false) }
        }
    }

    private suspend fun getLocalUser() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    private suspend fun getCachedMeetings() {
        val meetings = getCachedMeetingsUseCase.execute()
        setState { copy(meetings = meetings) }
    }

    private fun changeStatusBarState(toTransparent: Boolean) = setState {
        copy(transparentStatusBar = toTransparent)
    }

    private fun back() {
        changeStatusBarState(false)
        setEffect { Effect.OnBackClick }
    }
}