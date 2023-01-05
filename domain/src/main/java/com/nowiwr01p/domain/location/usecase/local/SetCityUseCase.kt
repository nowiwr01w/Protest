package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository
import com.nowiwr01p.domain.location.repository.LocationStateRemoteRepository

class SetCityUseCase(
    private val localRepository: LocationStateLocalRepository,
    private val remoteRepository: LocationStateRemoteRepository
): UseCase<City, Unit> {

    override suspend fun execute(input: City) {
        remoteRepository.setCity(input)
        localRepository.setCity(input)
    }
}