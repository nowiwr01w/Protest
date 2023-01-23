package com.nowiwr01p.meetings.ui.create_meeting.map

import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.user.usecase.GetLocalUserUseCase
import com.nowiwr01p.meetings.ui.create_meeting.map.CreateMeetingMapContract.*

class CreateMeetingMapViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.SelectCoordinates -> selectCoordinates(event.position)
            is Event.RemoveLastCoordinate -> removeLastCoordinate()
            is Event.OnSavePathClick -> savePath()
        }
    }

    /**
     * SET MEETING
     */
    private fun init() = io {
        getUserData()
    }

    /**
     * GET LOCAL USER DATA
     */
    private suspend fun getUserData() {
        val city = getLocalUserUseCase.execute().city
        val coordinates = LatLng(city.latitude, city.longitude)
        setState { copy(cityCoordinates = coordinates) }
    }

    /**
     * SELECTED COORDINATES
     */
    private fun selectCoordinates(position: LatLng) {
        val updated = viewState.value.selectedCoordinates.toMutableList().apply {
            add(position)
        }
        setState { copy(selectedCoordinates = updated) }
    }

    private fun removeLastCoordinate() {
        val updated = viewState.value.selectedCoordinates.toMutableList().apply {
            remove(last())
        }
        setState { copy(selectedCoordinates = updated) }
    }

    /**
     * BACK CLICK
     */
    private fun savePath() = setEffect {
        Effect.OnSavePathClick(viewState.value.selectedCoordinates)
    }
}