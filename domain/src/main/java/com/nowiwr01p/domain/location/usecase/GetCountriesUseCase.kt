package com.nowiwr01p.domain.location.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.domain.location.repository.LocationRepository

class GetCountriesUseCase(
    private val repository: LocationRepository
): UseCase<Unit, List<com.nowiwr01p.core.datastore.location.data.Country>> {

    override suspend fun execute(input: Unit): List<com.nowiwr01p.core.datastore.location.data.Country> {
        return repository.getCountries()
    }
}