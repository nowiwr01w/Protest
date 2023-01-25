package com.nowiwr01p.auth.ui.splash_screen

import com.nowiwr01p.auth.AuthScreen.*
import com.nowiwr01p.auth.ui.splash_screen.SplashScreenContract.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.auth.cities.usecase.local.GetLocalCityUseCase
import com.nowiwr01p.domain.auth.verification.usecase.GetLocalVerificationUseCase
import com.nowiwr01p.domain.categories.usecase.SubscribeCategoriesUseCase
import com.nowiwr01p.domain.meetings.main.usecase.SubscribeMeetingsUseCase
import com.nowiwr01p.domain.news.main.usecase.SubscribeNewsUseCase
import com.nowiwr01p.domain.stories.usecase.SubscribeStoriesUseCase
import com.nowiwr01p.domain.user.usecase.SubscribeUserUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class SplashScreenViewModel(
    private val subscribeUserUseCase: SubscribeUserUseCase,
    private val subscribeStoriesUseCase: SubscribeStoriesUseCase,
    private val subscribeNewsUseCase: SubscribeNewsUseCase,
    private val subscribeMeetingsUseCase: SubscribeMeetingsUseCase,
    private val subscribeCategoriesUseCase: SubscribeCategoriesUseCase,
    private val getLocalCityUseCase: GetLocalCityUseCase,
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
        async { subscribeUserIfAuthorized() },
        async { isCitySet() },
        async { isVerificationCompleted() },
        async { onContentSubscribe() }
    ).awaitAll()

    private suspend fun subscribeUserIfAuthorized() = runCatching {
        subscribeUserUseCase.execute()
    }.onSuccess {
        setState { copy(isAuthorized = true) }
    }.onFailure {
        setState { copy(isAuthorized = false) }
    }

    private suspend fun isCitySet() {
        val set = getLocalCityUseCase.execute().name.isNotEmpty()
        setState { copy(isCitySet = set) }
    }

    private suspend fun isVerificationCompleted() {
        val completed = getLocalVerificationUseCase.execute()
        setState { copy(isVerificationCompleted = completed) }
    }

    private suspend fun onContentSubscribe() = listOf(
        async { subscribeStoriesUseCase.execute() },
        async { subscribeMeetingsUseCase.execute() },
        async { subscribeCategoriesUseCase.execute() },
        async { subscribeNewsUseCase.execute() }
    ).awaitAll()

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