package com.nowiwr01p.domain.location.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.domain.location.repository.LocationPreferencesRepository

class SetCountryUseCase(
    private val repository: LocationPreferencesRepository
): UseCase<com.nowiwr01p.core.datastore.location.data.Country, Unit> {

    override suspend fun execute(input: com.nowiwr01p.core.datastore.location.data.Country) {
        repository.setCounty(input)
    }
}