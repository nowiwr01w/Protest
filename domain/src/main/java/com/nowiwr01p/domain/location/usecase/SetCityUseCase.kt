package com.nowiwr01p.domain.location.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.domain.location.repository.LocationPreferencesRepository

class SetCityUseCase(
    private val repository: LocationPreferencesRepository
): UseCase<com.nowiwr01p.core.datastore.location.data.City, Unit> {

    override suspend fun execute(input: com.nowiwr01p.core.datastore.location.data.City) {
        repository.setCity(input)
    }
}