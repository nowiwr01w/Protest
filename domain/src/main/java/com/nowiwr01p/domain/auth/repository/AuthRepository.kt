package com.nowiwr01p.domain.auth.repository

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.auth.data.user.UserData

interface AuthRepository {
    suspend fun signIn(userData: UserData): User
    suspend fun signUp(userData: UserData): User
}