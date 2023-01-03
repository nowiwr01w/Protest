package com.nowiwr01p.core.datastore.location.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    override val selected: Boolean = false,
    @SerializedName("city")
    override val name: String = "",
    @SerializedName("lat")
    val latitude: Double = .0,
    @SerializedName("lng")
    val longitude: Double = .0,
    @SerializedName("country")
    val country: String = "",
    @SerializedName("iso2")
    val iso2: String = "",
    @SerializedName("admin_name")
    val adminName: String = "",
    @SerializedName("capital")
    val capital: String = "",
    @SerializedName("population")
    val population: String = "",
    @SerializedName("population_proper")
    val populationProper: String = ""
): Location