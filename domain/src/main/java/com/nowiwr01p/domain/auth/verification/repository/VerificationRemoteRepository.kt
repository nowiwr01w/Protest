package com.nowiwr01p.domain.auth.verification.repository

interface VerificationRemoteRepository: VerificationLocalRepository {
    suspend fun sendEmailVerification()
}