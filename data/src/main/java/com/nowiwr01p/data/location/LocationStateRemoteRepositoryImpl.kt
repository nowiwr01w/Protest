package com.nowiwr01p.data.location

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.domain.location.repository.LocationStateRemoteRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRepository

class LocationStateRemoteRepositoryImpl(
    private val userRemoteRepository: UserRemoteRepository
): LocationStateRemoteRepository {

    override suspend fun getCity(): City {
        return userRemoteRepository.getUser().city
    }

    override suspend fun setCity(city: City) {
        val updated = userRemoteRepository.getUser().copy(city = city)
        userRemoteRepository.updateUser(updated)
    }

    override suspend fun getCountry(): Country {
        return userRemoteRepository.getUser().country
    }

    override suspend fun setCounty(country: Country) {
        val updated = userRemoteRepository.getUser().copy(country = country)
        userRemoteRepository.updateUser(updated)
    }
}