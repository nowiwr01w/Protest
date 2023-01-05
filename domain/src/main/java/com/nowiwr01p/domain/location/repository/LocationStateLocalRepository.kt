package com.nowiwr01p.domain.location.repository

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country

interface LocationStateLocalRepository {
    suspend fun getCity(): City
    suspend fun setCity(city: City)
    suspend fun getCountry(): Country
    suspend fun setCounty(country: Country)
}