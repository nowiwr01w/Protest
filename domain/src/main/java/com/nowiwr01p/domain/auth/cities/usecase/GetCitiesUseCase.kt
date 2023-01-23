package com.nowiwr01p.domain.auth.cities.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.domain.auth.cities.repository.CitiesRepository

class GetCitiesUseCase(
    private val repository: CitiesRepository
): UseCase<Unit, List<City>> {

    override suspend fun execute(input: Unit): List<City> {
        return repository.getCities()
    }
}