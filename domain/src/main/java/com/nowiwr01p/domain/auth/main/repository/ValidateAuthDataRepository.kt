package com.nowiwr01p.domain.auth.main.repository

import com.nowiwr01p.domain.auth.main.data.error.AuthError
import com.nowiwr01p.domain.auth.main.data.user.UserData

interface ValidateAuthDataRepository {
    suspend fun isAuthDataValid(userData: UserData): AuthError?
}