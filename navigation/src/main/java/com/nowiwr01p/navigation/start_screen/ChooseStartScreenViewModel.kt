package com.nowiwr01p.navigation.start_screen

import com.google.firebase.auth.FirebaseAuth
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.navigation.start_screen.ChooseStartScreenContract.*
import com.nowiwr01p.navigation.start_screen.data.StartScreenType.*
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.location.usecase.local.GetLocalCityUseCase
import com.nowiwr01p.domain.location.usecase.local.GetLocalCountryUseCase
import com.nowiwr01p.domain.verification.usecase.GetLocalVerificationUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class ChooseStartScreenViewModel(
    private val auth: FirebaseAuth,
    private val getLocalCityUseCase: GetLocalCityUseCase,
    private val getLocalCountryUseCase: GetLocalCountryUseCase,
    private val getLocalVerificationUseCase: GetLocalVerificationUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> getStartScreen()
        }
    }

    private fun getStartScreen() = io {
        checkLocalData()
        getStartScreenRoute()
    }

    private suspend fun checkLocalData() = listOf(
        async { isCitySet() },
        async { isCountrySet() },
        async { isVerificationCompleted() },
    ).awaitAll()

    private suspend fun isCitySet() {
        val set = getLocalCityUseCase.execute().name.isNotEmpty()
        setState { copy(isCitySet = set) }
    }

    private suspend fun isCountrySet() {
        val set = getLocalCountryUseCase.execute().name.isNotEmpty()
        setState { copy(isCountrySet = set) }
    }

    private suspend fun isVerificationCompleted() {
        val completed = getLocalVerificationUseCase.execute()
        setState { copy(isVerificationCompleted = completed) }
    }

    private fun getStartScreenRoute() = with(viewState.value) {
        val startScreen = when {
            auth.currentUser == null -> AUTH
            !isVerificationCompleted -> VERIFICATION
            !isCountrySet || !isCitySet -> COUNTRIES
            else -> MAP
        }
        setEffect { Effect.NavigateToStartScreen(startScreen.roure) }
    }
}