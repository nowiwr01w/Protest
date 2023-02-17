package com.nowiwr01p.domain.user.repository

import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.app.ReferencedListener
import kotlinx.coroutines.flow.StateFlow

interface UserRemoteRealtimeRepository {
    suspend fun getUserFlow(): StateFlow<User>
    suspend fun subscribeUser(): ReferencedListener
}