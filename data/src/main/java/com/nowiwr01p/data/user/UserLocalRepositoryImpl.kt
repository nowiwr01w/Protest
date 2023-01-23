package com.nowiwr01p.data.user

import androidx.datastore.core.DataStore
import com.nowiwr01p.core.datastore.user.UserPreference
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.user.repository.UserLocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class UserLocalRepositoryImpl(
    private val userStore: DataStore<UserPreference>,
    private val dispatchers: AppDispatchers
): UserLocalRepository {

    override suspend fun getUser() = withContext(dispatchers.io) {
        userStore.data.first().user
    }

    override suspend fun setUser(user: User): Unit = withContext(dispatchers.io) {
        userStore.updateData { it.copy(user = user) }
    }
}