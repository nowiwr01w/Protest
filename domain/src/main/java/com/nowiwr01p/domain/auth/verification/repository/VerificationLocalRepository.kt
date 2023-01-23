package com.nowiwr01p.domain.auth.verification.repository

interface VerificationLocalRepository {
    suspend fun isVerificationCompleted(): Boolean
    suspend fun setVerificationCompleted(completed: Boolean = true)
}