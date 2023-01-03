package com.nowiwr01p.domain.location.api.response

import com.nowiwr01p.core.datastore.location.data.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: List<Country>
)