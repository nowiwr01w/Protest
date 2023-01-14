package com.nowiwr01p.meetings.ui.create_meeting.map

import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface CreateMeetingMapContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object OnSavePathClick: Event
        object RemoveLastCoordinate: Event
        data class SelectCoordinates(val position: LatLng): Event
    }

    data class State(
        val cityCoordinates: LatLng = LatLng(.0, .0),
        val selectedCoordinates: List<LatLng> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        data class OnSavePathClick(val path: List<LatLng>): Effect
    }

    interface Listener {
        fun onBackClick()
        fun onSavePathClick()
        fun selectCoordinates(position: LatLng)
        fun removeLastCoordinate()
    }
}