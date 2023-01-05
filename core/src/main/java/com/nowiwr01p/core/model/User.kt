package com.nowiwr01p.core.model

import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("country")
    val country: Country = Country(),
    @SerialName("city")
    val city: City = City(),
    @SerialName("verified")
    val verified: Boolean = false,
    @SerialName("organizer")
    val organizer: Boolean = false
)