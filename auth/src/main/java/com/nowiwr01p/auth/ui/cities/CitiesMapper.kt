package com.nowiwr01p.auth.ui.cities

import com.nowiwr01p.auth.ui.cities.CitiesContract.*
import com.nowiwr01p.core_ui.mapper.ViewModelMapper
import com.nowiwr01p.core.datastore.cities.data.City

class CitiesMapper : ViewModelMapper<CitiesViewModel>() {

    fun selectCity(city: City) = viewModel.viewState.value.cities.map { curCity ->
        val selected = if (curCity == city) {
            curCity.selected.not().also { isSelected ->
                if (!isSelected) viewModel.setEvent(Event.ClearSelectedCity)
            }
        } else {
            false
        }
        curCity.copy(selected = selected)
    }
}