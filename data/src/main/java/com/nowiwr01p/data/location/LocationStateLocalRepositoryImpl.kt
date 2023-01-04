package com.nowiwr01p.data.location

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.location.LocationPreference
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository
import kotlinx.coroutines.flow.first

class LocationStateLocalRepositoryImpl(
    private val locationStore: DataStore<LocationPreference>
): LocationStateLocalRepository {

    override suspend fun isCitySet(): City {
        return locationStore.data.first().selectedCity
    }

    override suspend fun setCity(city: City) {
        locationStore.updateData {
            LocationPreference(selectedCountry = isCountrySet(), selectedCity = city)
        }
    }

    override suspend fun isCountrySet(): Country {
        return locationStore.data.first().selectedCountry
    }

    override suspend fun setCounty(country: Country) {
        locationStore.updateData {
            LocationPreference(selectedCountry = country)
        }
    }
}