package com.nowiwr01p.data.meetings.create_meeting

import com.nowiwr01p.core.datastore.cities.data.Coordinate
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CreateMeetingError
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CreateMeetingError.*
import com.nowiwr01p.domain.meetings.create_meeting.validators.CreateMeetingValidator

class CreateMeetingValidatorImpl: CreateMeetingValidator {

    private val errors = mutableListOf<CreateMeetingError?>()

    override suspend fun validate(meeting: Meeting) = with(meeting) {
        validateImageLink(image)
        validateCategories(categories)
        validateTitle(title)
        validateDescription(description)
        validateDate(date)
        validateLocationTitle(locationInfo.locationName)
        validateLocationCoordinates(locationInfo.locationStartPoint)
        validateLocationDetails(locationInfo.locationDetails)
        validateLocationPath(locationInfo.path)
        validateTelegramLink(telegram)
    }.let {
        errors.distinct().filterNotNull()
    }.also {
        errors.clear()
    }

    /**
     * TOP IMAGE LINK
     */
    override suspend fun validateImageLink(link: String): CreateMeetingError? = with(link) {
        val ext = startsWith("https://")
        val image = endsWith(".png") || endsWith(".jpg") || endsWith(".jpeg")
        when {
            !ext -> ImageLinkError.ExtensionError()
            !image -> ImageLinkError.ImageTypeError()
            else -> null
        }.also {
            addError(it)
        }
    }

    /**
     * CATEGORIES
     */
    override suspend fun validateCategories(categories: List<Category>) = when {
        categories.isEmpty() -> CategoriesError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * TITLE
     */
    override suspend fun validateTitle(title: String) = when {
        title.isEmpty() -> TitleError.EmptyTitleError()
        title.length > 48 -> TitleError.LongTitleError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * DESCRIPTION
     */
    override suspend fun validateDescription(description: String) = when {
        description.isEmpty() -> DescriptionError.EmptyDescriptionError()
        description.length > 300 -> DescriptionError.LongDescriptionError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * MEETING DATE
     */
    override suspend fun validateDate(date: Long) = when {
        date == 0L -> DateError.DateNotSelectedError()
        date < System.currentTimeMillis() -> DateError.DateBeforeError()
        date - System.currentTimeMillis() > 4 * 60 * 60 * 1000 -> DateError.EarlyDateError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * START LOCATION COORDINATES
     */
    override suspend fun validateLocationCoordinates(coordinate: Coordinate) = when {
        coordinate.latitude == .0 || coordinate.longitude == .0 -> StartLocationError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * START LOCATION TITLE
     */
    override suspend fun validateLocationTitle(location: String) = when {
        location.isEmpty() -> LocationTitleError.EmptyLocationTitleError()
        location.length > 18 -> LocationTitleError.LongLocationTitleError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * START LOCATION DETAILS
     */
    override suspend fun validateLocationDetails(details: String) = when {
        details.isEmpty() -> LocationDetailsError.EmptyLocationDetailsError()
        details.length > 48 -> LocationDetailsError.LongLocationDetailsError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * LOCATION PATH
     */
    override suspend fun validateLocationPath(path: List<Coordinate>) = when {
        path.size < 4 -> PathError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * TELEGRAM LINK
     */
    override suspend fun validateTelegramLink(link: String) = when {
        !link.startsWith("https://") -> TelegramLinkError.ExtensionError()
        !link.contains("t.me/") -> TelegramLinkError.DomainError()
        else -> null
    }.also {
        addError(it)
    }

    /**
     * ADD ERROR TO LIST
     */
    private fun addError(error: CreateMeetingError?) {
        errors.add(error)
    }
}