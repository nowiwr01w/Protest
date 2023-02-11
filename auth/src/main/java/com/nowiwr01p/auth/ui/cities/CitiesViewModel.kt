package com.nowiwr01p.auth.ui.cities

import androidx.compose.ui.graphics.Color
import com.nowiwr01p.auth.ui.cities.CitiesContract.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.core_ui.ui.status_bar.StatusBarColorHelper
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.auth.cities.usecase.GetCitiesUseCase
import com.nowiwr01p.domain.auth.cities.usecase.local.SetCityUseCase

class CitiesViewModel(
    private val statusBarColor: Color,
    private val statusBarColorHelper: StatusBarColorHelper,
    private val getCities: GetCitiesUseCase,
    private val setCity: SetCityUseCase,
    private val mapper: CitiesMapper
): BaseViewModel<Event, State, Effect>() {

    private var allCities = listOf<City>()

    init {
        mapper.viewModel = this
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.CityClick -> selectCity(event.city)
            is Event.ClearSelectedCity -> setState { copy(selectedCity = null) }
            is Event.ConfirmClick -> onConfirmClicked()
            is Event.OnSearchStateChanged -> onSearchStateChanged(event.value)
        }
    }

    private fun init() {
        statusBarColorHelper.setStatusBarColor(statusBarColor)
        getCities()
    }

    private fun selectCity(city: City) {
        saveCity(city)
        val updated = mapper.selectCity(city)
        setCities(updated)
        setState { copy(selectedCity = city) }
    }

    private fun saveCity(city: City) = io {
        setCity.execute(city)
    }

    private fun getCities() = io {
        runCatching {
            getCities.execute().sortedByDescending { it.population.toIntOrNull() }
        }.onSuccess {
            setCities(it).run { allCities = it }
        }
    }

    private fun setCities(cities: List<City>) {
        setState { copy(cities = cities, loaded = true) }
    }

    private fun onSearchStateChanged(value: String) {
        val filtered = allCities.filter { it.name.lowercase().contains(value.lowercase()) }
        setState { copy(searchValue = value, cities = filtered) }
    }

    private fun onConfirmClicked() {
        setEffect { Effect.ShowNextScreen }
        setState { copy(selectedCity = null) }
    }
}