package com.nowiwr01p.core.datastore.location.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Meeting(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("creatorId")
    val creatorId: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("date")
    val date: Long = 0L,
    @SerializedName("openDate")
    val openDate: OpenDate = OpenDate(),
    @SerializedName("categories")
    val categories: List<String> = listOf(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("locationInfo")
    val locationInfo: LocationInfo = LocationInfo(),
    @SerializedName("hideLocationUntil")
    val hideLocationUntil: HideLocationUntil = HideLocationUntil(),
    @SerializedName("takeWithYouInfo")
    val takeWithYouInfo: TakeWithYouInfo = TakeWithYouInfo(),
    @SerializedName("details")
    val details: Details = Details(),
    @SerializedName("reaction")
    val reaction: Reaction = Reaction(),
    @SerializedName("telegram")
    val telegram: String = "",
    @SerializedName("path")
    val path: List<Coordinate> = listOf(),
    @SerializedName("currentPosition")
    val currentPosition: Coordinate = Coordinate()
)

@Serializable
data class Coordinate(
    @SerialName("latitude")
    val latitude: Double = .0,
    @SerialName("longitude")
    val longitude: Double = .0,
)

@Serializable
data class OpenDate(
    @SerializedName("test")
    val test: String = "",
    @SerializedName("requiredPeopleCount")
    val requiredPeopleCount: Int = 0
)

@Serializable
data class HideLocationUntil(
    @SerializedName("date")
    val date: Long = 0L,
    @SerializedName("requiredPeopleCount")
    val requiredPeopleCount: Int = 0
)

@Serializable
data class LocationInfo(
    @SerializedName("shortName")
    val shortName: String = "",
    @SerializedName("coordinates")
    val coordinates: Coordinate = Coordinate(),
    @SerializedName("placeDetails")
    val placeDetails: String = ""
)

@Serializable
data class TakeWithYouInfo(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("posterLinks")
    val posterLinks: List<String> = listOf()
)

@Serializable
data class Details(
    @SerializedName("goals")
    val goals: List<String> = listOf(),
    @SerializedName("slogans")
    val slogans: List<String> = listOf(),
    @SerializedName("strategy")
    val strategy: List<String> = listOf(),
)

@Serializable
data class Reaction(
    @SerializedName("peopleGoCount")
    val peopleGoCount: Int = 0,
    @SerializedName("peopleMaybeGoCount")
    val peopleMaybeGoCount: Int = 0,
    @SerializedName("yourChoiceToGo")
    val yourChoiceToGo: Boolean? = null
)