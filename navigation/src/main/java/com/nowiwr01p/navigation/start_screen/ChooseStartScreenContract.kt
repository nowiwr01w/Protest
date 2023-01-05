package com.nowiwr01p.navigation.start_screen

import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.navigation.start_screen.data.StartScreenType

interface ChooseStartScreenContract {

    interface Event: ViewEvent {
        object Init: Event
    }

    data class State(
        val isCitySet: Boolean = false,
        val isCountrySet: Boolean = false,
        val isVerificationCompleted: Boolean = false
    ): ViewState

    interface Effect: ViewSideEffect {
        data class NavigateToStartScreen(val route: StartScreenType): Effect
    }
}