package com.nowiwr01p.domain.meetings.create_meeting.validators

import com.nowiwr01p.core.datastore.cities.data.Coordinate
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CreateMeetingError

interface CreateMeetingValidator {
    suspend fun validate(meeting: Meeting): List<CreateMeetingError>
    suspend fun validateImageLink(link: String): CreateMeetingError?
    suspend fun validateCategories(categories: List<Category>): CreateMeetingError?
    suspend fun validateTitle(title: String): CreateMeetingError?
    suspend fun validateDescription(description: String): CreateMeetingError?
    suspend fun validateDate(date: Long): CreateMeetingError?
    suspend fun validateLocationCoordinates(coordinate: Coordinate): CreateMeetingError?
    suspend fun validateLocationTitle(location: String): CreateMeetingError?
    suspend fun validateLocationDetails(details: String): CreateMeetingError?
    suspend fun validateLocationPath(path: List<Coordinate>): CreateMeetingError?
    suspend fun validateTelegramLink(link: String): CreateMeetingError?
}