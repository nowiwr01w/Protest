package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository
import com.nowiwr01p.domain.location.repository.LocationStateRemoteRepository
import com.nowiwr01p.domain.user.UserRemoteRepository

class IsCountrySetUseCase(
    private val userRemoteRepository: UserRemoteRepository,
    private val localRepository: LocationStateLocalRepository,
    private val remoteRepository: LocationStateRemoteRepository
): UseCase<Unit, Country> {

    override suspend fun execute(input: Unit): Country {
        return if (userRemoteRepository.isUserAuthorized()) {
            remoteRepository.isCountrySet()
        } else {
            localRepository.isCountrySet()
        }
    }
}