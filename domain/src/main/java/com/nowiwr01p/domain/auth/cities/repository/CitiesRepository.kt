package com.nowiwr01p.domain.auth.cities.repository

import com.nowiwr01p.core.datastore.cities.data.City

interface CitiesRepository {
    suspend fun getCities(): List<City>
}