package com.nowiwr01p.domain.cities.api.response

import com.nowiwr01p.core.datastore.cities.data.City
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: List<City>
)