package com.nowiwr01p.domain.auth.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("hasCountry")
    val hasCountry: Boolean = false,
    @SerialName("hasCity")
    val hasCity: Boolean = false,
    @SerialName("organizer")
    val organizer: Boolean = false
)