package com.nowiwr01p.data.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.extensions.getAccount
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository
import com.nowiwr01p.domain.user.repository.UserDataStoreRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import com.nowiwr01p.domain.verification.repository.VerificationLocalRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRemoteRepositoryImpl(
    private val auth: FirebaseAuth,
    private val references: FirebaseReferencesRepository,
    private val verificationLocalRepository: VerificationLocalRepository,
    private val locationStateLocalRepository: LocationStateLocalRepository,
    private val userDataStoreRepository: UserDataStoreRepository,
    private val dispatchers: AppDispatchers
): UserRemoteRepository {

    override suspend fun getFirebaseUser(): FirebaseUser {
        return auth.currentUser ?: throw IllegalStateException("User expected")
    }

    override suspend fun isUserAuthorized(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun getUser() = withContext(dispatchers.io) {
        val firebaseUser = getFirebaseUser()
        val userSnapshot = references.getUserReference(firebaseUser.uid).get().await()
        userSnapshot.getAccount().also { it.setLocalData() }
    }

    override suspend fun updateUser(user: User) = withContext(dispatchers.io) {
        val firebaseUser = getFirebaseUser()
        references.getUserReference(firebaseUser.uid).setValue(user).await()
        user.also { it.setLocalData() }
    }

    private suspend fun User.setLocalData() {
        locationStateLocalRepository.setCity(city)
        locationStateLocalRepository.setCounty(country)
        verificationLocalRepository.setVerificationCompleted(verified)
        userDataStoreRepository.setUser(this)
    }
}