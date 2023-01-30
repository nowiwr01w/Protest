package com.nowiwr01p.core.datastore.cities.data

import com.nowiwr01p.core.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class MeetingDB(
    val id: String = "",
    val cityName: String = "",
    val creatorId: String = "",
    val date: Long = 0L,
    val requiredPeopleCount: Int = 0,
    val image: String = "",
    val categories: List<Category> = listOf(),
    val title: String = "",
    val description: String = "",
    val takeWithYouInfo: TakeWithYouInfo = TakeWithYouInfo(),
    val details: Details = Details(),
    val reaction: Reaction = Reaction(),
    val telegram: String = "",
    val currentPosition: Coordinate = Coordinate()
) {
    companion object {
        fun toMeetingDB(meeting: Meeting) = MeetingDB(
            id = meeting.id,
            cityName = meeting.cityName,
            creatorId = meeting.creatorId,
            date = meeting.date,
            requiredPeopleCount = meeting.requiredPeopleCount,
            image = meeting.image,
            categories = meeting.categories,
            title = meeting.title,
            description = meeting.description,
            takeWithYouInfo = meeting.takeWithYouInfo,
            details = meeting.details,
            reaction = meeting.reaction,
            telegram = meeting.telegram,
            currentPosition = meeting.currentPosition
        )
    }
}