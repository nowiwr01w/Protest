package com.nowiwr01p.domain.user.repository

import com.nowiwr01p.core.model.User

interface UserLocalRepository {
    suspend fun getUser(): User
    suspend fun setUser(user: User)
}