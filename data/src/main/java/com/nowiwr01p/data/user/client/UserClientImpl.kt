package com.nowiwr01p.data.user.client

import com.nowiwr01p.core.extenstion.createUserEventListener
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.user.client.UserClient
import com.nowiwr01p.domain.user.repository.UserLocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserClientImpl(
    private val references: FirebaseReferencesRepository,
    private val localRepository: UserLocalRepository,
    private val dispatchers: AppDispatchers
): UserClient {

    private val userFlow: MutableStateFlow<User> = MutableStateFlow(User())

    override suspend fun getUserFlow(): StateFlow<User> = userFlow

    override suspend fun subscribeUser(): Unit = withContext(dispatchers.io) {
        val listener = createUserEventListener { user ->
            CoroutineScope(dispatchers.io).launch { saveUser(user) }
        }
        localRepository.getUser().id.let { userId ->
            if (userId.isNotEmpty()) {
                references.getUserReference(userId).addValueEventListener(listener)
            } else {
                throw IllegalStateException("Can't subscribe empty user")
            }
        }
    }

    private suspend fun saveUser(user: User) {
        localRepository.setUser(user)
        userFlow.emit(user)
    }
}