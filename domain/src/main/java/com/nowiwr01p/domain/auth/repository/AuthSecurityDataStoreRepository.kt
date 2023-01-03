package com.nowiwr01p.domain.auth.repository

interface AuthSecurityDataStoreRepository {
    suspend fun isAuthSecurityWarningShown(): Boolean
    suspend fun setAuthSecurityWarningShown()
}