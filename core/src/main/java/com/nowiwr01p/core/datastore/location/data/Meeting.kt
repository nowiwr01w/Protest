package com.nowiwr01p.core.datastore.location.data

import com.nowiwr01p.core.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val id: String = "",
    val cityName: String = "",
    val creatorId: String = "",
    val image: String = "",
    val date: Long = 0L,
    val openDate: OpenDate = OpenDate(),
    val categories: List<Category> = listOf(),
    val title: String = "",
    val description: String = "",
    val locationInfo: LocationInfo = LocationInfo(),
    val takeWithYouInfo: TakeWithYouInfo = TakeWithYouInfo(),
    val details: Details = Details(),
    val reaction: Reaction = Reaction(),
    val telegram: String = "",
    val path: List<Coordinate> = listOf(),
    val currentPosition: Coordinate = Coordinate()
) {
    companion object {
        fun getSampleData() = Meeting(
            id = "123",
            cityName = "Saint-Petersburg",
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
            telegram = "nowiwr01p",
            currentPosition = Coordinate.getSampleData()
        )
    }
}

@Serializable
data class Coordinate(
    val latitude: Double = .0,
    val longitude: Double = .0,
) {
    companion object {
        fun getSampleData() = Coordinate(59.938946, 30.314982)
    }
}

@Serializable
data class OpenDate(
    val requiredPeopleCount: Int = 0
) {
    companion object {
        fun getSampleData() = OpenDate(requiredPeopleCount = 1000)
    }
}

@Serializable
data class LocationInfo(
    val shortName: String = "",
    val coordinates: Coordinate = Coordinate(),
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
    val description: String = "",
    val posters: List<Poster> = listOf()
) {
    companion object {
        fun getSampleData() = TakeWithYouInfo(
            description = "Плакаты, флаги, что угодно\nВот примеры:",
            posters = listOf(
                Poster("Плакат", "https://google.com/search?q=first", ""),
                Poster("Плакат", "https://google.com/search?q=second", ""),
                Poster("Плакат", "https://google.com/search?q=third", ""),
                Poster("Плакат", "https://google.com/search?q=fourth", ""),
                Poster("Плакат", "https://google.com/search?q=fifth", "")
            )
        )
    }
}

@Serializable
data class Poster(
    val name: String = "",
    val link: String = "",
    val image: String = ""
)

@Serializable
data class Details(
    val goals: List<String> = listOf(),
    val slogans: List<String> = listOf(),
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
    val peopleGoCount: List<String> = listOf(),
    val peopleMaybeGoCount: List<String> = listOf()
) {
    companion object {
        fun getSampleData() = Reaction(
            peopleGoCount = listOf(),
            peopleMaybeGoCount = listOf()
        )
    }
}