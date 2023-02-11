package com.nowiwr01p.auth.ui.splash_screen

import com.google.firebase.auth.FirebaseAuth
import com.nowiwr01p.auth.AuthScreen.*
import com.nowiwr01p.auth.ui.splash_screen.SplashScreenContract.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.app.GetSplashScreenAnimationStateUseCase
import com.nowiwr01p.domain.app.InitAppDataUseCase
import com.nowiwr01p.domain.auth.cities.usecase.local.GetLocalCityUseCase
import com.nowiwr01p.domain.auth.verification.usecase.GetRemoteVerificationUseCase
import com.nowiwr01p.domain.execute
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class SplashScreenViewModel(
    private val auth: FirebaseAuth,
    private val initAppDataUseCase: InitAppDataUseCase,
    private val getSplashScreenAnimationStateUseCase: GetSplashScreenAnimationStateUseCase,
    private val getLocalCityUseCase: GetLocalCityUseCase,
    private val getRemoteVerificationUseCase: GetRemoteVerificationUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> getStartScreen()
        }
    }

    private fun getStartScreen() = io {
        getSplashScreenAnimation()
        checkLocalData()
        getStartScreenRoute()
    }

    /**
     * SPLASH SCREEN ANIMATION
     */
    private suspend fun getSplashScreenAnimation() {
        getSplashScreenAnimationStateUseCase.execute().let { demo ->
            setState { copy(isSplashScreenDemoAnimation = demo) }
        }
    }

    /**
     * CHOOSE START SCREEN BY THIS DATA
     */
    private suspend fun checkLocalData() = runCatching {
        listOf(
            async { isAuthorized() },
            async { isCitySet() },
            async { isVerificationCompleted() },
        ).awaitAll()
    }

    /**
     * AUTHORIZED USER STATE
     */
    private fun isAuthorized() = runCatching {
        auth.currentUser != null
    }.onSuccess { authorized ->
        setState { copy(isAuthorized = authorized) }
        if (authorized) {
            initAppData()
        }
    }

    /**
     * SUBSCRIBE ON ALL OF THE DATA
     */
    private fun initAppData() = io {
        runCatching {
            initAppDataUseCase.execute()
        }
    }

    /**
     * CITY STATE
     */
    private suspend fun isCitySet() {
        val set = getLocalCityUseCase.execute().name.isNotEmpty()
        setState { copy(isCitySet = set) }
    }

    /**
     * VERIFICATION STATE
     */
    private suspend fun isVerificationCompleted() {
        val completed = getRemoteVerificationUseCase.execute()
        setState { copy(isVerificationCompleted = completed) }
    }

    /**
     * ROUTE OF THE START SCREEN
     */
    private fun getStartScreenRoute() = with(viewState.value) {
        val startScreen = when {
            !isAuthorized -> AuthMainScreen.route
            !isVerificationCompleted -> VerificationMainScreen.route
            !isCitySet -> CitiesMainScreen.route
            else -> "meetings_main_screen"
        }
        setState { copy(route = startScreen) }
    }
}