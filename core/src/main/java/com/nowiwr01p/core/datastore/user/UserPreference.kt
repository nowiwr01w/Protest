package com.nowiwr01p.core.datastore.user

import com.nowiwr01p.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val user: User = User()
)