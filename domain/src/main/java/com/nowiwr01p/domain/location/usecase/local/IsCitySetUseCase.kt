package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository
import com.nowiwr01p.domain.location.repository.LocationStateRemoteRepository
import com.nowiwr01p.domain.user.UserRemoteRepository

class IsCitySetUseCase(
    private val userRemoteRepository: UserRemoteRepository,
    private val locationStateLocalRepository: LocationStateLocalRepository,
    private val locationStateRemoteRepository: LocationStateRemoteRepository,
): UseCase<Unit, City> {

    override suspend fun execute(input: Unit): City {
        return if (userRemoteRepository.isUserAuthorized()) {
            locationStateRemoteRepository.isCitySet()
        } else {
            locationStateLocalRepository.isCitySet()
        }
    }
}