package com.nowiwr01p.data.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.nowiwr01p.core.firebase.FirebaseConst.USERS_REFERENCE
import com.nowiwr01p.domain.auth.data.user.User
import com.nowiwr01p.domain.extensions.getAccount
import com.nowiwr01p.domain.user.UserRemoteRepository
import kotlinx.coroutines.tasks.await

class UserRemoteRepositoryImpl(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
): UserRemoteRepository {

    override suspend fun getFirebaseUser(): FirebaseUser {
        return auth.currentUser ?: throw IllegalStateException("User expected")
    }

    override suspend fun isUserAuthorized(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun getUser(): User {
        val firebaseUser = getFirebaseUser()
        val userSnapshot = database.getReference(USERS_REFERENCE)
            .child(firebaseUser.uid)
            .get()
            .await()
        return userSnapshot.getAccount()
    }

    override suspend fun updateUser(user: User): User {
        val firebaseUser = getFirebaseUser()
        database.getReference(USERS_REFERENCE)
            .child(firebaseUser.uid)
            .setValue(user)
            .await()
        return user
    }
}