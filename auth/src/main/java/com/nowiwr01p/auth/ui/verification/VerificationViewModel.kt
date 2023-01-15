package com.nowiwr01p.auth.ui.verification

import androidx.lifecycle.viewModelScope
import com.nowiwr01p.auth.ui.verification.VerificationContract.*
import com.nowiwr01p.auth.ui.verification.data.Mode.*
import com.nowiwr01p.core_ui.ui.button.ButtonState.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.verification.usecase.GetRemoteVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.SendEmailVerificationUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.verification.usecase.SetVerificationCompletedUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class VerificationViewModel(
    private val sendVerification: SendEmailVerificationUseCase,
    private val checkVerification: GetRemoteVerificationUseCase,
    private val setVerificationCompleted: SetVerificationCompletedUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> startTimer()
            is Event.ResendCode -> resendCode()
            is Event.CheckIsEmailVerified -> checkVerified()
            is Event.NavigateToCities -> setEffect { Effect.NavigateToCities }
        }
    }

    private fun checkVerified() = io {
        setState { copy(buttonState = SEND_REQUEST) }
        runCatching {
            checkVerification.execute()
        }.onSuccess {
            onCheckVerificationSuccess(it)
        }.onFailure {
            onCheckVerificationFailed()
        }
    }

    private suspend fun onCheckVerificationSuccess(verified: Boolean) {
        if (verified) {
            setVerificationCompleted.execute()
            setState { copy(buttonState = SUCCESS) }
        } else {
            onCheckVerificationFailed()
        }
    }

    private suspend fun onCheckVerificationFailed() {
        setState { copy(buttonState = ERROR) }
        delay(3000)
        setState { copy(buttonState = DEFAULT) }
    }

    private fun resendCode() = io {
        setState { copy(resendProgress = true) }
        runCatching {
            sendVerification.execute()
        }.onSuccess {
            startTimer()
            setState { copy(resendProgress = false) }
        }.onFailure {
            setState { copy(resendProgress = false) }
        }
    }

    private fun startTimer() = (120 downTo 1).asSequence()
        .asFlow()
        .onStart {
            setState { copy(mode = TIMER) }
        }
        .onEach {
            setState { copy(timerSeconds = it) }
            delay(1000)
        }
        .onCompletion {
            setState { copy(mode = SEND_AGAIN_TEXT) }
        }
        .launchIn(viewModelScope)
}