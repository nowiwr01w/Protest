package com.nowiwr01p.core.model

import com.nowiwr01p.core.datastore.cities.data.City
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val email: String = "",
    val city: City = City(),
    val name: String = "",
    val avatar: String = "",
    val verified: Boolean = false,
    val writer: Boolean = true,
    val organizer: Boolean = true,
)