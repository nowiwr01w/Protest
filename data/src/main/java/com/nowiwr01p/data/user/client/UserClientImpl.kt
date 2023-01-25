package com.nowiwr01p.data.user.client

import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.user.client.UserClient
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserClientImpl(
    private val references: FirebaseReferencesRepository,
    private val repository: UserRemoteRepository,
    private val dispatchers: AppDispatchers
): UserClient {

    private val userFlow: MutableStateFlow<User> = MutableStateFlow(User())

    override suspend fun getUserFlow(): StateFlow<User> = userFlow

    override suspend fun subscribeUser(): Unit = withContext(dispatchers.io) {
        val listener = createEventListener<User> { user ->
            CoroutineScope(dispatchers.io).launch { userFlow.emit(user) }
        }
        val userId = repository.getFirebaseUser().uid
        references.getUserReference(userId).addValueEventListener(listener)
    }
}