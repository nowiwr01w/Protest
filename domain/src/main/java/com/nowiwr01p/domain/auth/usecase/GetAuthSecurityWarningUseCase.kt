package com.nowiwr01p.domain.auth.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.auth.repository.AuthSecurityDataStoreRepository

class GetAuthSecurityWarningUseCase(
    private val repository: AuthSecurityDataStoreRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return repository.isAuthSecurityWarningShown()
    }
}