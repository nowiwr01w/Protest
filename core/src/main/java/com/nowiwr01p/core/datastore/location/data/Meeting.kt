package com.nowiwr01p.core.datastore.location.data

import com.google.gson.annotations.SerializedName
import com.nowiwr01p.core.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
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
    val categories: List<Category> = listOf(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("locationInfo")
    val locationInfo: LocationInfo = LocationInfo(),
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
) {
    companion object {
        fun getSampleData() = Meeting(
            id = "123",
            creatorId = "1234",
            image = "https://krasnoturinsk.info/upload/resize_cache/iblock/d28/855_420_1/d28b130dbc0228bd99ae97369489a808.jpg",
            date = System.currentTimeMillis(),
            openDate = OpenDate.getSampleData(),
            categories = Category.getSampleData(),
            title = "Свободу Навальному",
            description = "Ни для кого не секрет, что в России права человека считаются чем-то, что всегда " +
                    "уходит на задний план.\n\n" +
                    "Алексей Навальный старался изменить ситуацию, но стал жертвой режима. Его посадили " +
                    "в тюрьму за то, что он говорил правду.\n" +
                    "Так быть не должно.",
            locationInfo = LocationInfo.getSampleData(),
            takeWithYouInfo = TakeWithYouInfo.getSampleData(),
            details = Details.getSampleData(),
            reaction = Reaction.getSampleData(),
            telegram = "nowiwr01p"
        )
    }
}

@Serializable
data class Coordinate(
    @SerialName("latitude")
    val latitude: Double = .0,
    @SerialName("longitude")
    val longitude: Double = .0,
) {
    companion object {
        fun getSampleData() = Coordinate(59.938946, 30.314982)
    }
}

@Serializable
data class OpenDate(
    @SerializedName("text")
    val text: String = "",
    @SerializedName("date")
    val date: Long = 0L,
    @SerializedName("requiredPeopleCount")
    val requiredPeopleCount: Int = 0
) {
    companion object {
        fun getSampleData() = OpenDate(
            text = "Место и дата проведения появится либо 23.02, либо после того, как наберётся хотя бы 1000 человек.\nЭто обязательное условие.",
            date = System.currentTimeMillis(),
            requiredPeopleCount = 1000
        )
    }
}

@Serializable
data class LocationInfo(
    @SerializedName("shortName")
    val shortName: String = "",
    @SerializedName("coordinates")
    val coordinates: Coordinate = Coordinate(),
    @SerializedName("placeDetails")
    val placeDetails: String = ""
) {
    companion object {
        fun getSampleData() = LocationInfo(
            shortName = "Дворцовая площадь",
            coordinates = Coordinate(59.938946, 30.314982),
            placeDetails = "Рядом со сценой"
        )
    }
}

@Serializable
data class TakeWithYouInfo(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("posterLinks")
    val posterLinks: List<String> = listOf()
) {
    companion object {
        fun getSampleData() = TakeWithYouInfo(
            description = "Плакаты, флаги, что угодно\nВот примеры:",
            posterLinks = listOf(
                "https://google.com/search?q=first",
                "https://google.com/search?q=second",
                "https://google.com/search?q=third",
                "https://google.com/search?q=fourth",
                "https://google.com/search?q=fifth",
            )
        )
    }
}

@Serializable
data class Details(
    @SerializedName("goals")
    val goals: List<String> = listOf(),
    @SerializedName("slogans")
    val slogans: List<String> = listOf(),
    @SerializedName("strategy")
    val strategy: List<String> = listOf(),
) {
    companion object {
        fun getSampleData() = Details(
            goals = listOf("Путин - хуйло", "Ла", "Лалала", "Лалалалааааа"),
            slogans = listOf("Путин - хуйло", "Ла", "Лалала", "Лалалалааааа"),
            strategy = listOf("Путин - хуйло", "Ла", "Лалала", "Лалалалааааа"),
        )
    }
}

@Serializable
data class Reaction(
    @SerializedName("peopleGoCount")
    val peopleGoCount: List<String> = listOf(),
    @SerializedName("peopleMaybeGoCount")
    val peopleMaybeGoCount: List<String> = listOf()
) {
    companion object {
        fun getSampleData() = Reaction(
            peopleGoCount = listOf(),
            peopleMaybeGoCount = listOf()
        )
    }
}