package com.nowiwr01p.auth.ui

import com.nowiwr01p.auth.ui.AuthContract.*
import com.nowiwr01p.auth.ui.data.AuthTextFieldType
import com.nowiwr01p.auth.ui.data.AuthTextFieldType.*
import com.nowiwr01p.auth.ui.data.AuthType.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel

class AuthViewModel: BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.OnAuthClick -> auth()
            is Event.ToggleAuthMode -> toggleAuthMode()
            is Event.TogglePasswordVisibility -> togglePasswordVisibility()
            is Event.OnValueChanged -> changeValue(event.type, event.value)
        }
    }

    private fun init() {

    }

    private fun togglePasswordVisibility() = setState {
        copy(hidePassword = !hidePassword)
    }

    private fun toggleAuthMode() = setState {
        val authType = if (viewState.value.authType == SIGN_IN) SIGN_UP else SIGN_IN
        copy(authType = authType)
    }

    private fun changeValue(type: AuthTextFieldType, value: String) = setState {
        when (type) {
            EMAIL -> copy(email = value)
            PASSWORD -> copy(password = value)
            PASSWORD_REPEAT -> copy(passwordRepeat = value)
        }
    }

    private fun auth() {

    }
}