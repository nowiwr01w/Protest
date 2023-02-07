package com.nowiwr01p.auth.ui.splash_screen

import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface SplashScreenContract {

    interface Event: ViewEvent {
        object Init: Event
    }

    data class State(
        val route: String = "",
        val isCitySet: Boolean = false,
        val isCountrySet: Boolean = false,
        val isAuthorized: Boolean = false,
        val isVerificationCompleted: Boolean = false,
        val isSplashScreenDemoAnimation: Boolean = false
    ): ViewState

    interface Effect: ViewSideEffect
}