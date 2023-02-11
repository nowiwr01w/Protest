package com.nowiwr01p.domain.auth.verification.repository

interface VerificationRemoteRepository {
    suspend fun isVerificationCompleted(): Boolean
    suspend fun sendEmailVerification()
}