package com.nowiwr01p.core.datastore.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthSecurityWarning(
    val shown: Boolean = false
)