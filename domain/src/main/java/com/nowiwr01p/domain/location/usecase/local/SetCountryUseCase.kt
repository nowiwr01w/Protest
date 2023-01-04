package com.nowiwr01p.domain.location.usecase.local

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.domain.location.repository.LocationDataStoreRepository

class SetCountryUseCase(
    private val repository: LocationDataStoreRepository
): UseCase<Country, Unit> {

    override suspend fun execute(input: Country) {
        repository.setCounty(input)
    }
}