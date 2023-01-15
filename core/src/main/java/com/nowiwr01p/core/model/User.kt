package com.nowiwr01p.core.model

import com.nowiwr01p.core.datastore.cities.data.City
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("city")
    val city: City = City(),
    @SerialName("verified")
    val verified: Boolean = false,
    @SerialName("organizer")
    val organizer: Boolean = true
)