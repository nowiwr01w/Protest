package com.nowiwr01p.map.ui

import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface MapContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object OnBackClick: Event
    }

    data class State(
        val user: User = User(),
        val showProgress: Boolean = true,
        val transparentStatusBar: Boolean = false
    ): ViewState {
        val coordinates: LatLng
            get() = LatLng(user.city.latitude, user.city.longitude)
    }

    sealed interface Effect: ViewSideEffect{
        object OnBackClick: Effect
    }

    interface Listener {
        fun onBack()
    }
}