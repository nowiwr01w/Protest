package com.nowiwr01p.data.auth.cities

import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.domain.auth.cities.repository.CityStateRemoteRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRealtimeRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRepository

class CitiesStateRemoteRepositoryImpl(
    private val userRemoteRealtimeRepository: UserRemoteRealtimeRepository,
    private val userRemoteRepository: UserRemoteRepository
): CityStateRemoteRepository {

    override suspend fun getCity(): City {
        return userRemoteRealtimeRepository.getUserFlow().value.city
    }

    override suspend fun setCity(city: City) {
        val updated = userRemoteRealtimeRepository.getUserFlow().value.copy(city = city)
        userRemoteRepository.setUser(updated)
    }
}