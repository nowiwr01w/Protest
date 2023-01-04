package com.nowiwr01p.domain.verification.repository

interface VerificationLocalRepository {
    suspend fun isVerificationCompleted(): Boolean
    suspend fun setVerificationCompleted()
}