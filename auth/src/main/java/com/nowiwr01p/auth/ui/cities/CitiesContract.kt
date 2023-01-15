package com.nowiwr01p.auth.ui.cities

import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface CitiesContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        data class CityClick(val city: City): Event
        data class OnSearchStateChanged(val value: String): Event
        object ConfirmClick: Event
        object ClearSelectedCity: Event
    }

    data class State(
        val loaded: Boolean = false,
        val searchValue: String = "",
        val selectedCity: City? = null,
        val cities: List<City> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        data class ShowNextScreen(val city: City): Effect
        data class ConfirmClick(val city: City): Effect
    }

    interface Listener {
        fun onBackClick()
        fun onConfirmClick()
        fun onCityClick(city: City)
        fun onSearchStateChanged(value: String)
    }
}