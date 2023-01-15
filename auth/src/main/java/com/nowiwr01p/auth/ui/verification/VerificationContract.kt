package com.nowiwr01p.auth.ui.verification

import com.nowiwr01p.auth.ui.verification.data.Mode
import com.nowiwr01p.core_ui.ui.button.ButtonState
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface VerificationContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object ResendCode: Event
        object NavigateToCities: Event
        object CheckIsEmailVerified: Event
    }

    data class State(
        val mode: Mode = Mode.TIMER,
        val timerSeconds: Int = 120,
        val resendProgress: Boolean = false,
        val buttonState: ButtonState = ButtonState.DEFAULT
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateToCities: Effect
    }

    interface Listener {
        fun resendCode()
        fun onCheckCode()
        fun toCities()
    }
}