package com.nowiwr01p.auth.ui.cities

import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.core_ui.ui.button.ButtonState
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface CitiesContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object ConfirmClick: Event
        object ClearSelectedCity: Event
        object ShowNextScreen: Event
        data class CityClick(val city: City): Event
        data class OnSearchStateChanged(val value: String): Event
    }

    data class State(
        val loaded: Boolean = false,
        val searchValue: String = "",
        val selectedCity: City? = null,
        val cities: List<City> = listOf(),
        val confirmButtonState: ButtonState = ButtonState.DEFAULT
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object ShowNextScreen: Effect
    }

    interface Listener {
        fun onBackClick()
        fun onConfirmClick()
        fun showNextScreen()
        fun onCityClick(city: City)
        fun onSearchStateChanged(value: String)
    }
}