package com.nowiwr01p.data.location

import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.location.api.LocationApi
import com.nowiwr01p.domain.location.repository.LocationRepository
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val api: LocationApi,
    private val dispatchers: AppDispatchers
): LocationRepository {

    override suspend fun getCountries() = withContext(dispatchers.io) {
        api.getCountries()
    }

    override suspend fun getCities(country: String) = withContext(dispatchers.io) {
        api.getCities()
    }
}