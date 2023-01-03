package com.nowiwr01p.domain.auth.repository

import com.nowiwr01p.domain.auth.data.error.AuthError
import com.nowiwr01p.domain.auth.data.user.UserData

interface ValidateAuthDataRepository {
    suspend fun isAuthDataValid(userData: UserData): AuthError?
}