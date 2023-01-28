package com.nowiwr01p.core.datastore.cities.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    val selected: Boolean = false,
    @SerializedName("city")
    val name: String = "",
    @SerializedName("lat")
    val latitude: Double = .0,
    @SerializedName("lng")
    val longitude: Double = .0,
    @SerializedName("district")
    val district: String = "",
    @SerializedName("population")
    val population: String = "",
    @SerializedName("subject")
    val subject: String = ""
)
