package com.nowiwr01p.domain.location.repository

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country

interface LocationRepository {
    suspend fun getCities(country: String): List<City>
    suspend fun getCountries(): List<Country>
}