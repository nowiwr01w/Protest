package com.nowiwr01p.domain.auth.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.main.repository.AuthSecurityDataStoreRepository

class GetAuthSecurityWarningUseCase(
    private val repository: AuthSecurityDataStoreRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return repository.isAuthSecurityWarningShown()
    }
}