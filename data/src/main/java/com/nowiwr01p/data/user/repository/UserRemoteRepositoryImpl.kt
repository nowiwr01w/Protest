package com.nowiwr01p.data.user.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.auth.cities.repository.CityStateLocalRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRemoteRepositoryImpl(
    private val auth: FirebaseAuth,
    private val references: FirebaseReferencesRepository,
    private val cityStateLocalRepository: CityStateLocalRepository,
    private val dispatchers: AppDispatchers
): UserRemoteRepository {

    override suspend fun getFirebaseUser(): FirebaseUser {
        return auth.currentUser ?: throw IllegalStateException("User expected")
    }

    override suspend fun getUser() = withContext(dispatchers.io) {
        val firebaseUser = getFirebaseUser()
        val userSnapshot = references.getUserReference(firebaseUser.uid).get().await()
        userSnapshot.getValue<User>()!!.also { it.setLocalData() }
    }

    override suspend fun setUser(user: User) = withContext(dispatchers.io) {
        val firebaseUser = getFirebaseUser()
        references.getUserReference(firebaseUser.uid).setValue(user).await()
        user.also { it.setLocalData() }
    }

    private suspend fun User.setLocalData() {
        cityStateLocalRepository.setCity(city)
    }
}