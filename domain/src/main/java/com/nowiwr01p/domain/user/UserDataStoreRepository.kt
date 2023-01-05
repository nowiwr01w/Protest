package com.nowiwr01p.domain.user

import com.nowiwr01p.core.model.User

interface UserDataStoreRepository {
    suspend fun getUser(): User
    suspend fun setUser(user: User)
}