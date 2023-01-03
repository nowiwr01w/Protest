package com.nowiwr01p.core.datastore

import kotlinx.serialization.Serializable

@Serializable
data class AuthSecurityWarning(
    val shown: Boolean = false
)