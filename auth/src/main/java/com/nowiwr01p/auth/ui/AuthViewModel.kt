package com.nowiwr01p.auth.ui

import com.nowiwr01p.auth.ui.AuthContract.*
import com.nowiwr01p.auth.ui.data.AuthType.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType
import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType.*
import com.nowiwr01p.domain.auth.data.user.UserData
import com.nowiwr01p.domain.auth.data.user.UserDataSignIn
import com.nowiwr01p.domain.auth.data.user.UserDataSignUp
import com.nowiwr01p.domain.auth.usecase.ValidateAuthDataUseCase

class AuthViewModel(
    private val authDataValidator: ValidateAuthDataUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.OnAuthClick -> checkUserInput()
            is Event.ToggleAuthMode -> toggleAuthMode()
            is Event.TogglePasswordVisibility -> togglePasswordVisibility()
            is Event.OnValueChanged -> changeValue(event.type, event.value)
        }
    }

    private fun init() {
        // TODO
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

    private fun checkUserInput() = io {
        getUserData().let { userData ->
            val error = authDataValidator.execute(userData)
            if (error == null) {
                auth(userData)
            } else {
                setEffect { Effect.ShowError(error) }
            }
            setState { copy(authError = error) }
        }
    }

    private fun getUserData() = with(viewState.value) {
        when (viewState.value.authType) {
            SIGN_IN -> UserDataSignIn(email, password)
            SIGN_UP -> UserDataSignUp(email, password, passwordRepeat)
        }
    }

    private suspend fun auth(userData: UserData) = runCatching {
        // TODO
    }
}