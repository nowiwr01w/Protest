package com.nowiwr01p.data.auth.cities

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.cities.CitiesPreference
import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.domain.auth.cities.repository.CityStateLocalRepository
import kotlinx.coroutines.flow.first

class CitiesStateLocalRepositoryImpl(
    private val citiesStore: DataStore<CitiesPreference>
): CityStateLocalRepository {

    override suspend fun getCity(): City {
        return citiesStore.data.first().selectedCity
    }

    override suspend fun setCity(city: City) {
        citiesStore.updateData { it.copy(selectedCity = city) }
    }
}