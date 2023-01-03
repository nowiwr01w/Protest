package com.nowiwr01p.data.auth.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.auth.data.user.User
import com.nowiwr01p.domain.auth.data.user.UserData
import com.nowiwr01p.domain.auth.repository.AuthRepository
import com.nowiwr01p.domain.extensions.hasAccount
import com.nowiwr01p.domain.extensions.toUser
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val dispatchers: AppDispatchers
): AuthRepository {

    override suspend fun signIn(userData: UserData) = withContext(dispatchers.io) {
        val authResult = auth.signInWithEmailAndPassword(userData.email, userData.password)
            .asDeferred()
            .await()
        if (authResult.user != null) {
            getUserAccount(authResult)
        } else {
            throw IllegalStateException("Auth: firebaseUser = null")
        }
    }

    override suspend fun signUp(userData: UserData) = withContext(dispatchers.io) {
        val user = auth.createUserWithEmailAndPassword(userData.email, userData.password)
            .asDeferred()
            .await()
        saveFirebaseUser(user.toUser())
    }

    private suspend fun getUserAccount(user: AuthResult): User {
        database.getReference(USERS_REFERENCE).get().await().children.forEach { snapshot ->
            snapshot.hasAccount(user).let { resultUser ->
                if (resultUser != null) return resultUser
            }
        }
        throw IllegalStateException("Bro, you're completely wrong. We always have user here.")
    }

    private suspend fun saveFirebaseUser(user: User): User {
        database.getReference(USERS_REFERENCE).child(user.id).setValue(user).await()
        return user
    }

    private companion object {
        const val USERS_REFERENCE = "users"
    }
}