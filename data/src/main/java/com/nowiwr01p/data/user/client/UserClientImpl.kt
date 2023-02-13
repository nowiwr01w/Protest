package com.nowiwr01p.data.user.client

import com.google.firebase.auth.FirebaseAuth
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.user.client.UserClient
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserClientImpl(
    private val auth: FirebaseAuth,
    private val references: FirebaseReferencesRepository,
    private val repository: UserRemoteRepository,
    private val dispatchers: AppDispatchers
): UserClient {

    private val userFlow: MutableStateFlow<User> = MutableStateFlow(User())

    override suspend fun getUserFlow(): StateFlow<User> = userFlow

    /**
     * GET REMOTE USER WITH REALTIME UPDATED
     */
    override suspend fun subscribeUser(): Unit = withContext(dispatchers.io) {
        val listener = createEventListener<User> { user ->
            val updatedUser = setUserPermissions(user)
            userFlow.emit(updatedUser)
        }
        val userId = repository.getFirebaseUser().uid
        references.getUserReference(userId).addValueEventListener(listener)
    }

    /**
     * CHECK USER PERMISSIONS AND SET IN
     */
    private suspend fun setUserPermissions(user: User) = getUserCustomClaims().run {
        user.copy(
            writer = getCustomClaimValue("writer"),
            organizer = getCustomClaimValue("organizer"),
            organizerEverywhere = getCustomClaimValue("organizerEverywhere"),
            tempWriter = getCustomClaimValue("tempWriter"),
            tempOrganizer = getCustomClaimValue("tempOrganizer"),
            admin = getCustomClaimValue("admin")
        )
    }

    /**
     * USER CUSTOM CLAIMS
     */
    private suspend fun getUserCustomClaims() = auth.currentUser
        ?.getIdToken(true)
        ?.await()
        ?.claims
        .orEmpty()

    private fun Map<String, Any>.getCustomClaimValue(key: String): Boolean {
        return (this[key] as? Boolean) ?: false
    }
}