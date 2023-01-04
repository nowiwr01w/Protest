package com.nowiwr01p.auth.ui.location

import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.core.datastore.location.data.Location

interface LocationContract {

    sealed interface Event: ViewEvent {
        data class Init(val countryCode: String): Event
        data class LocationClick(val location: Location): Event
        data class OnSearchStateChanged(val value: String): Event
        object ConfirmClick: Event
        object ClearSelectedLocation: Event
    }

    data class State(
        val loaded: Boolean = false,
        val searchValue: String = "",
        val selectedLocation: Location? = null,
        val locations: List<Location> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        data class ShowNextScreen(val location: Location): Effect
        data class ConfirmClick(val location: Location): Effect
    }

    interface Listener {
        fun onBackClick()
        fun onConfirmClick()
        fun onLocationClick(location: Location)
        fun onSearchStateChanged(value: String)
    }
}