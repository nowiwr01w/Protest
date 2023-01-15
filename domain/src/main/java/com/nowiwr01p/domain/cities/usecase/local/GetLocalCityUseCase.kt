package com.nowiwr01p.domain.cities.usecase.local

import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.cities.repository.CityStateLocalRepository

class GetLocalCityUseCase(
    private val repository: CityStateLocalRepository
): UseCase<Unit, City> {

    override suspend fun execute(input: Unit): City {
        return repository.getCity()
    }
}