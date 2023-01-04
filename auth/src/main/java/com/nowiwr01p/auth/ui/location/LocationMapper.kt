package com.nowiwr01p.auth.ui.location

import com.nowiwr01p.auth.ui.location.LocationContract.*
import com.nowiwr01p.core_ui.mapper.ViewModelMapper
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.core.datastore.location.data.Location

class LocationMapper : ViewModelMapper<LocationViewModel>() {

    fun selectLocation(location: Location) = viewModel.viewState.value.locations.map { curLocation ->
        val selected = if (curLocation == location) {
            curLocation.selected.not().also { isSelected ->
                if (!isSelected) viewModel.setEvent(Event.ClearSelectedLocation)
            }
        } else {
            false
        }
        when (curLocation) {
            is City -> curLocation.copy(selected = selected)
            is Country -> curLocation.copy(selected = selected)
        }
    }
}