package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository
import com.nowiwr01p.domain.location.repository.LocationStateRemoteRepository
import com.nowiwr01p.domain.user.UserRemoteRepository

class SetCountryUseCase(
    private val localRepository: LocationStateLocalRepository,
    private val remoteRepository: LocationStateRemoteRepository
): UseCase<Country, Unit> {

    override suspend fun execute(input: Country) {
        localRepository.setCounty(input)
        remoteRepository.setCounty(input)
    }
}