package com.nowiwr01p.auth.ui

import com.nowiwr01p.auth.ui.data.AuthTextFieldType
import com.nowiwr01p.auth.ui.data.AuthType
import com.nowiwr01p.auth.ui.data.AuthType.*
import com.nowiwr01p.core_ui.ui.ButtonState
import com.nowiwr01p.core_ui.ui.ButtonState.*
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface AuthContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object ToggleAuthMode: Event
        object OnAuthClick: Event
        data class OnValueChanged(val type: AuthTextFieldType, val value: String): Event
    }

    data class State(
        val authType: AuthType = SIGN_IN,
        val authButtonState: ButtonState = DEFAULT,
        val showKeyboard: Boolean = false,
        val email: String = "",
        val password: String = "",
        val passwordRepeat: String = ""
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateToChooseLocation: Effect
    }

    interface Listener {
        fun authClick()
        fun toggleAccountMode()
        fun onValueChanged(type: AuthTextFieldType, value: String)
    }
}