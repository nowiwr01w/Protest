package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository

class IsCitySetUseCase(
    private val locationStateLocalRepository: LocationStateLocalRepository
): UseCase<Unit, City> {

    override suspend fun execute(input: Unit): City {
        return locationStateLocalRepository.isCitySet()
    }
}