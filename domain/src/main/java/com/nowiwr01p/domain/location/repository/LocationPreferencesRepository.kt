package com.nowiwr01p.domain.location.repository

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country

interface LocationPreferencesRepository {
    suspend fun isCitySet(): com.nowiwr01p.core.datastore.location.data.City
    suspend fun setCity(city: com.nowiwr01p.core.datastore.location.data.City)
    suspend fun isCountrySet(): com.nowiwr01p.core.datastore.location.data.Country
    suspend fun setCounty(country: com.nowiwr01p.core.datastore.location.data.Country)
}