package com.nowiwr01p.data.auth.main.repository

import com.google.firebase.auth.FirebaseAuth
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.auth.main.data.user.UserData
import com.nowiwr01p.domain.auth.main.repository.AuthRepository
import com.nowiwr01p.core.extenstion.toUser
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val userRepository: UserRemoteRepository,
    private val dispatchers: AppDispatchers
): AuthRepository {

    /**
     * SIGN IN
     */
    override suspend fun signIn(userData: UserData) = withContext(dispatchers.io) {
        val authResult = auth.signInWithEmailAndPassword(userData.email, userData.password)
            .asDeferred()
            .await()
        if (authResult.user != null) {
            userRepository.getUser()
        } else {
            throw IllegalStateException("Неверный email или password")
        }
    }

    /**
     * SIGN UP
     */
    override suspend fun signUp(userData: UserData) = withContext(dispatchers.io) {
        val user = auth.createUserWithEmailAndPassword(userData.email, userData.password)
            .asDeferred()
            .await()
            .toUser()
        userRepository.updateUser(user)
    }
}