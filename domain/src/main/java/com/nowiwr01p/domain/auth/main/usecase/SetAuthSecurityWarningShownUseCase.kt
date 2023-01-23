package com.nowiwr01p.domain.auth.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.main.repository.AuthSecurityDataStoreRepository

class SetAuthSecurityWarningShownUseCase(
    private val repository: AuthSecurityDataStoreRepository
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        repository.setAuthSecurityWarningShown()
    }
}