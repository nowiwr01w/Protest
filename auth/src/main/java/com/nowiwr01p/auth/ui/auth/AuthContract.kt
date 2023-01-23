package com.nowiwr01p.auth.ui.auth

import com.nowiwr01p.auth.ui.auth.data.AuthType
import com.nowiwr01p.auth.ui.auth.data.AuthType.SIGN_IN
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.button.ButtonState
import com.nowiwr01p.core_ui.ui.button.ButtonState.DEFAULT
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinkListener
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.domain.auth.main.data.error.AuthError
import com.nowiwr01p.domain.auth.main.data.error.AuthTextFieldType

interface AuthContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object OnAuthClick: Event
        object ToggleAuthMode: Event
        object NavigateToVerification: Event
        object NavigateToChooseCountry: Event
        object NavigateToMeetings: Event
        object TogglePasswordVisibility: Event
        data class OpenLink(val link: String): Event
        data class ShowBottomSheet(val params: BottomSheetParams): Event
        data class OnValueChanged(val type: AuthTextFieldType, val value: String): Event
    }

    data class State(
        val authType: AuthType = SIGN_IN,
        val authButtonState: ButtonState = DEFAULT,
        val showKeyboard: Boolean = false,
        val hidePassword: Boolean = true,
        val authSecurityWarningWasShown: Boolean = false,
        val authError: AuthError? = null,
        val email: String = "",
        val password: String = "",
        val passwordRepeat: String = "",
        val isUserVerified: Boolean = false,
        val isUserSetCity: Boolean = false
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateToMeetings: Effect
        object NavigateToVerification: Effect
        object NavigateToChooseCountry: Effect
        object ShowAuthSecurityWarning: Effect
    }

    interface Listener: OpenLinkListener {
        fun authClick()
        fun toNextScreen()
        fun toggleAccountMode()
        fun togglePasswordVisibility()
        fun onValueChanged(type: AuthTextFieldType, value: String)
    }
}