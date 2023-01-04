package com.nowiwr01p.core.datastore.verification

import kotlinx.serialization.Serializable

@Serializable
data class VerificationPreference(
    val completed: Boolean = false
)