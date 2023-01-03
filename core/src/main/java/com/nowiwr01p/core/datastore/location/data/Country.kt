package com.nowiwr01p.core.datastore.location.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    override val selected: Boolean = false,
    @SerializedName("name")
    override val name: String = "",
    @SerializedName("code")
    val code: String = ""
): Location