package com.nowiwr01p.domain.user.client

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.app.EventListener
import kotlinx.coroutines.flow.StateFlow

interface UserClient {
    suspend fun getUserFlow(): StateFlow<User>
    suspend fun subscribeUser(): EventListener
}