package com.nowiwr01p.auth.ui.location

import com.nowiwr01p.auth.ui.location.LocationContract.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.core.datastore.location.data.Location
import com.nowiwr01p.domain.location.usecase.GetCitiesUseCase
import com.nowiwr01p.domain.location.usecase.GetCountriesUseCase
import com.nowiwr01p.domain.location.usecase.SetCityUseCase
import com.nowiwr01p.domain.location.usecase.SetCountryUseCase

class LocationViewModel(
    private val getCities: GetCitiesUseCase,
    private val getCountries: GetCountriesUseCase,
    private val setCity: SetCityUseCase,
    private val setCountry: SetCountryUseCase,
    private val mapper: LocationMapper
): BaseViewModel<Event, State, Effect>() {

    init {
        mapper.viewModel = this
    }

    private var allLocations = listOf<Location>()

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.countryCode)
            is Event.LocationClick -> selectLocation(event.location)
            is Event.ClearSelectedLocation -> setState { copy(selectedLocation = null) }
            is Event.ConfirmClick -> onConfirmClicked()
            is Event.OnSearchStateChanged -> onSearchStateChanged(event.value)
        }
    }

    private fun init(countryCode: String) {
        if (countryCode.isNotEmpty()) {
            getCities(countryCode)
        } else {
            getCountries()
        }
    }

    private fun selectLocation(location: Location) {
        saveLocation(location)
        val updated = mapper.selectLocation(location)
        setLocations(updated)
        setState { copy(selectedLocation = location) }
    }

    private fun saveLocation(location: Location) = io {
        when (location) {
            is City -> setCity.execute(location)
            is Country -> setCountry.execute(location)
        }
    }

    private fun getCities(countryCode: String) = io {
        runCatching {
            getCities.execute(countryCode).sortedByDescending { it.population.toIntOrNull() }
        }.onSuccess {
            setLocations(it).run { allLocations = it }
        }
    }

    private fun getCountries() = io {
        runCatching {
            getCountries.execute(Unit).filter { it.code == "RU" }
        }.onSuccess {
            setLocations(it).run { allLocations = it }
        }
    }

    private fun setLocations(locations: List<Location>) {
        setState { copy(locations = locations, loaded = true) }
    }

    private fun onSearchStateChanged(value: String) {
        val filtered = allLocations.filter { it.name.lowercase().contains(value.lowercase()) }
        setState { copy(searchValue = value, locations = filtered) }
    }

    private fun onConfirmClicked() {
        viewState.value.selectedLocation?.let {
            setEffect { Effect.ShowNextScreen(it) }
            setState { copy(selectedLocation = null) }
        }
    }
}