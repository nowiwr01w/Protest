package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.domain.location.repository.LocationDataStoreRepository

class SetCityUseCase(
    private val repository: LocationDataStoreRepository
): UseCase<City, Unit> {

    override suspend fun execute(input: City) {
        repository.setCity(input)
    }
}