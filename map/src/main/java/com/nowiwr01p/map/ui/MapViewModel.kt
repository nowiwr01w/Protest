package com.nowiwr01p.map.ui

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.location.usecase.local.GetLocalCityUseCase
import com.nowiwr01p.map.ui.MapContract.*

class MapViewModel(
    private val getCityUseCase: GetLocalCityUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
        }
    }

    private fun init() = io {
        runCatching {
            getCity()
        }.onSuccess {
            setState { copy(showProgress = false) }
        }
    }

    private suspend fun getCity() {
        val city = getCityUseCase.execute()
        setState { copy(city = city) }
    }
}