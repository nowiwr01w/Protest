package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.location.repository.LocationDataStoreRepository

class IsCitySetUseCase(
    private val repository: LocationDataStoreRepository
): UseCase<Unit, City> {

    override suspend fun execute(input: Unit): City {
        return repository.isCitySet()
    }
}