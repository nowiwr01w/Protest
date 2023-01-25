package com.nowiwr01p.domain.user.repository

import com.google.firebase.auth.FirebaseUser
import com.nowiwr01p.core.model.User

interface UserRemoteRepository {
    suspend fun getUser(): User
    suspend fun getFirebaseUser(): FirebaseUser
    suspend fun updateUser(user: User): User
}