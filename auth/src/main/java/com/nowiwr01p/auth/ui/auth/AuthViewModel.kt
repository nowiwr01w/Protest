package com.nowiwr01p.auth.ui.auth

import com.nowiwr01p.auth.ui.auth.AuthContract.*
import com.nowiwr01p.auth.ui.auth.data.AuthType.*
import com.nowiwr01p.core_ui.ui.ButtonState.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType
import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType.*
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.domain.auth.data.user.UserData
import com.nowiwr01p.domain.auth.data.user.UserDataSignIn
import com.nowiwr01p.domain.auth.data.user.UserDataSignUp
import com.nowiwr01p.domain.auth.usecase.*
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.verification.usecase.SendEmailVerificationUseCase
import kotlinx.coroutines.delay

class AuthViewModel(
    private val authDataValidator: ValidateAuthDataUseCase,
    private val authSecurityWarning: GetAuthSecurityWarningUseCase,
    private val setAuthSecurityWarningShown: SetAuthSecurityWarningShownUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val showSnackBarHelper: ShowSnackBarHelper,
    private val showBottomSheetHelper: ShowBottomSheetHelper,
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.OnAuthClick -> checkUserInput()
            is Event.TogglePasswordVisibility -> togglePasswordVisibility()
            is Event.OnValueChanged -> changeValue(event.type, event.value)
            is Event.ToggleAuthMode -> toggleAuthMode()
            is Event.NavigateToMap -> setEffect { Effect.NavigateToMap }
            is Event.NavigateToVerification -> setEffect { Effect.NavigateToVerification }
            is Event.NavigateToChooseCountry -> setEffect { Effect.NavigateToChooseCountry }
            is Event.ShowBottomSheet -> showBottomSheetHelper.showBottomSheet(event.content)
        }
    }

    private fun init() = io {
        getAuthSecurityWarning()
    }

    private fun togglePasswordVisibility() = setState {
        copy(hidePassword = !hidePassword)
    }

    private suspend fun getAuthSecurityWarning() {
        val shown = authSecurityWarning.execute()
        setState { copy(authSecurityWarningWasShown = shown) }
    }

    private suspend fun setAuthSecurityWarningShown() {
        setAuthSecurityWarningShown.execute()
        setState { copy(authSecurityWarningWasShown = true) }
    }

    private fun changeValue(type: AuthTextFieldType, value: String) = setState {
        when (type) {
            EMAIL -> copy(email = value)
            PASSWORD -> copy(password = value)
            PASSWORD_REPEAT -> copy(passwordRepeat = value)
        }
    }

    private fun toggleAuthMode() = io {
        val authType = if (viewState.value.authType == SIGN_IN) SIGN_UP else SIGN_IN
        setState { copy(authType = authType) }
        if (!viewState.value.authSecurityWarningWasShown) {
            setEffect { Effect.ShowAuthSecurityWarning }
            setAuthSecurityWarningShown()
        }
    }

    private fun checkUserInput() = io {
        getUserData().let { userData ->
            val error = authDataValidator.execute(userData)
            if (error == null) {
                auth(userData)
            } else {
                showSnackBarHelper.showSnackBar(error.message)
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

    private fun auth(userData: UserData) = io {
        setState { copy(authButtonState = SEND_REQUEST) }
        runCatching {
            when (userData) {
                is UserDataSignIn -> signInUseCase.execute(userData)
                is UserDataSignUp -> signUpUseCase.execute(userData)
            }
        }.onSuccess {
            checkVerification(it)
        }.onFailure {
            onAuthFailed()
        }
    }

    private suspend fun checkVerification(user: User) {
        if (user.verified) {
            setUserWasLoggedInBefore(user)
        } else {
            runCatching {
                sendEmailVerificationUseCase.execute()
            }.onSuccess {
                setState { copy(authButtonState = SUCCESS) }
            }.onFailure {
                onAuthFailed()
            }
        }
    }

    private fun setUserWasLoggedInBefore(user: User) = setState {
        copy(
            isUserVerified = true,
            isUserSetCity = user.city.name.isNotEmpty(),
            authButtonState = SUCCESS
        )
    }

    private suspend fun onAuthFailed() {
        setState { copy(authButtonState = ERROR) }
        delay(3000)
        setState { copy(authButtonState = DEFAULT) }
    }
}