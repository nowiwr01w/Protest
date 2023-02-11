package com.nowiwr01p.core.model

import com.nowiwr01p.core.datastore.cities.data.City
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val email: String = "",
    val city: City = City(),
    val name: String = "",
    val avatar: String = "",
    val writer: Boolean = false,
    val organizer: Boolean = false,
    val organizerEverywhere: Boolean = false
)