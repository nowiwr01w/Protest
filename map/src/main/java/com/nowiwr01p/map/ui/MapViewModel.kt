package com.nowiwr01p.map.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.map.ui.MapContract.*

class MapViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase
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
        }.onSuccess {
            setState { copy(showProgress = false) }
        }
    }

    private suspend fun getLocalUser() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    private fun changeStatusBarState(toTransparent: Boolean) = setState {
        copy(transparentStatusBar = toTransparent)
    }

    private fun back() {
        changeStatusBarState(false)
        setEffect { Effect.OnBackClick }
    }
}