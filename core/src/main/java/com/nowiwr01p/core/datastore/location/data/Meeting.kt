package com.nowiwr01p.core.datastore.location.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("title")
    val title: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("telegram")
    val telegram: String = "",
    @SerialName("path")
    val path: List<Coordinate> = listOf(),
    @SerialName("startDate")
    val startDate: Long = 0L,
    @SerialName("endDate")
    val endDate: Long = 0L,
    @SerialName("people")
    val people: Int = 1,
    @SerialName("leaders")
    val leaders: Int = 1,
    @SerialName("everyday")
    val everyday: Boolean = false,
    @SerialName("secret")
    val secret: Boolean = false,
    @SerialName("currentPosition")
    val currentPosition: Coordinate = Coordinate()
)

@Serializable
data class Coordinate(
    @SerialName("latitude")
    val latitude: Double = .0,
    @SerialName("longitude")
    val longitude: Double = .0,
)