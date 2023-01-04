package com.nowiwr01p.domain.verification.repository

interface VerificationRemoteRepository: VerificationLocalRepository {
    suspend fun sendVerification()
}