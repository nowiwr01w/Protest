package com.nowiwr01p.domain.auth.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.repository.AuthSecurityDataStoreRepository

class SetAuthSecurityWarningShownUseCase(
    private val repository: AuthSecurityDataStoreRepository
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        repository.setAuthSecurityWarningShown()
    }
}