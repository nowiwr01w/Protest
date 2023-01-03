package com.nowiwr01p.auth.ui.location

import com.nowiwr01p.core_ui.mapper.ViewModelMapper
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.core.datastore.location.data.Location

class LocationMapper : ViewModelMapper<LocationViewModel>() {

    fun selectLocation(location: Location) = viewModel.viewState.value.locations.map {
        when (it) {
            is City -> it.copy(selected = it == location)
            is Country -> it.copy(selected = it == location)
        }
    }
}