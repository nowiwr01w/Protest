package com.nowiwr01p.domain.location.repository

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country

interface LocationRepository {
    suspend fun getCities(country: String): List<com.nowiwr01p.core.datastore.location.data.City>
    suspend fun getCountries(): List<com.nowiwr01p.core.datastore.location.data.Country>
}