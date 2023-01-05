package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository

class IsCountrySetUseCase(
    private val localRepository: LocationStateLocalRepository
): UseCase<Unit, Country> {

    override suspend fun execute(input: Unit): Country {
        return localRepository.isCountrySet()
    }
}