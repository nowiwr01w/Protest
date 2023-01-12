package com.nowiwr01p.meetings.ui.create_meeting.map_draw_path

import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.datastore.location.data.Coordinate
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface MapDrawPathContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object RemoveLastCoordinate: Event
        data class SelectCoordinates(val position: LatLng): Event
    }

    data class State(
        val cityCoordinates: LatLng = LatLng(.0, .0),
        val selectedCoordinates: List<LatLng> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect

    interface Listener {
        fun onBackClick()
        fun onSavePathClick()
        fun selectCoordinates(position: LatLng)
        fun removeLastCoordinate()
    }
}