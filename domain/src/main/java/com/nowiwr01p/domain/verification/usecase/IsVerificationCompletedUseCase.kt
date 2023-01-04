package com.nowiwr01p.domain.verification.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.user.UserRemoteRepository
import com.nowiwr01p.domain.verification.repository.VerificationLocalRepository
import com.nowiwr01p.domain.verification.repository.VerificationRemoteRepository

class IsVerificationCompletedUseCase(
    private val userRemoteRepository: UserRemoteRepository,
    private val localRepository: VerificationLocalRepository,
    private val remoteRepository: VerificationRemoteRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit): Boolean {
        return if (userRemoteRepository.isUserAuthorized()) {
            remoteRepository.isVerificationCompleted()
        } else {
            localRepository.isVerificationCompleted()
        }
    }
}