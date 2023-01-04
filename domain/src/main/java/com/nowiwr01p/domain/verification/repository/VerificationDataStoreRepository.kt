package com.nowiwr01p.domain.verification.repository

interface VerificationDataStoreRepository {
    suspend fun isVerificationCompleted(): Boolean
    suspend fun setVerificationCompleted()
}