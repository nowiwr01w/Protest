package com.nowiwr01p.domain.auth.main.repository

interface AuthSecurityDataStoreRepository {
    suspend fun isAuthSecurityWarningShown(): Boolean
    suspend fun setAuthSecurityWarningShown()
}