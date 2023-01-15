package com.nowiwr01p.data.cities

import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.cities.api.CitiesApi
import com.nowiwr01p.domain.cities.repository.CitiesRepository
import kotlinx.coroutines.withContext

class CitiesRepositoryImpl(
    private val api: CitiesApi,
    private val dispatchers: AppDispatchers
): CitiesRepository {

    override suspend fun getCities() = withContext(dispatchers.io) {
        api.getCities()
    }
}