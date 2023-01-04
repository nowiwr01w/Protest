package com.nowiwr01p.domain.location.repository

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country

interface LocationDataStoreRepository {
    suspend fun isCitySet(): City
    suspend fun setCity(city: City)
    suspend fun isCountrySet(): Country
    suspend fun setCounty(country: Country)
}