package com.nowiwr01p.domain.auth.cities.usecase.local

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.domain.auth.cities.repository.CityStateLocalRepository
import com.nowiwr01p.domain.auth.cities.repository.CityStateRemoteRepository

class SetCityUseCase(
    private val localRepository: CityStateLocalRepository,
    private val remoteRepository: CityStateRemoteRepository
): UseCase<City, Unit> {

    override suspend fun execute(input: City) {
        remoteRepository.setCity(input)
        localRepository.setCity(input)
    }
}