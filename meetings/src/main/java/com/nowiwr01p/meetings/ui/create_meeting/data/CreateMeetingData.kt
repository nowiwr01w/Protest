package com.nowiwr01p.meetings.ui.create_meeting.data

import com.nowiwr01p.core.datastore.location.data.Coordinate
import com.nowiwr01p.core.model.Category

sealed class CreateMeetingData {

    data class Image(
        val link: String
    ): CreateMeetingData()

    data class Categories(
        val categories: List<Category>
    ): CreateMeetingData()

    data class Title(
        val text: String
    ): CreateMeetingData()

    data class Description(
        val text: String
    ): CreateMeetingData()

    data class Date(
        val date: Long,
        val place: String,
        val placeDetails: String,
        val startLocation: Coordinate,
        val path: List<Coordinate>
    ): CreateMeetingData()

    data class OpenDate(
        val date: Long,
        val peopleCount: Int
    ): CreateMeetingData()

    data class TakeWithYou(
        val text: String,
        val posters: List<String>
    )

    data class Goals(
        val goals: List<String>
    )

    data class Slogans(
        val goals: List<String>
    )

    data class Strategy(
        val goals: List<String>
    )
}