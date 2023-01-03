package com.nowiwr01p.domain.location.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.domain.location.repository.LocationRepository

class GetCitiesUseCase(
    private val repository: LocationRepository
): UseCase<String, List<City>> {

    override suspend fun execute(input: String): List<City> {
        return repository.getCities(input)
    }
}