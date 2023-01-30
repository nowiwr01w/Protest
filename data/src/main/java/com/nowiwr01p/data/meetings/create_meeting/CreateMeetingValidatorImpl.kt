package com.nowiwr01p.data.meetings.create_meeting

import com.nowiwr01p.core.datastore.cities.data.Coordinate
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.config.CreateMeetingRemoteConfig
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CreateMeetingError
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CreateMeetingError.*
import com.nowiwr01p.domain.meetings.create_meeting.validators.CreateMeetingValidator

class CreateMeetingValidatorImpl(config: CreateMeetingRemoteConfig): CreateMeetingValidator {

    private val errors = mutableListOf<CreateMeetingError?>()

    private val textLength = config.getTextLength()
    private val titleLength = config.getTitleLength()
    private val hoursDifference = config.getHoursDifference()
    private val locationPlaceLength = config.getLocationPlaceLength()
    private val locationDetailsLength = config.getLocationDetailsLength()
    private val pathDotsCount = config.getPathDotsCount()

    override suspend fun validate(meeting: Meeting) = with(meeting) {
        validateImageLink(image)
        validateCategories(categories)
        validateTitle(title)
        validateDescription(description)
        validateDate(locationInfo.date)
        if (cityName != "everywhere") {
            validateLocationTitle(locationInfo.locationName)
            validateLocationCoordinates(locationInfo.locationStartPoint)
            validateLocationDetails(locationInfo.locationDetails)
            validateLocationPath(locationInfo.path)
            validateTelegramLink(telegram)
        }
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
        val image = endsWith(".png") || endsWith(".jpg") || endsWith(".jpeg") || endsWith(".webp")
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
        title.length > titleLength.value -> TitleError.LongTitleError(titleLength.value)
        else -> null
    }.also {
        addError(it)
    }

    /**
     * DESCRIPTION
     */
    override suspend fun validateDescription(description: String) = when {
        description.isEmpty() -> DescriptionError.EmptyDescriptionError()
        description.length > textLength.value -> DescriptionError.LongDescriptionError(textLength.value)
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
        date - System.currentTimeMillis() < hoursDifference.value * 60 * 60 * 1000 -> {
            DateError.EarlyDateError(hoursDifference.value)
        }
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
        location.length > locationPlaceLength.value -> LocationTitleError.LongLocationTitleError(locationPlaceLength.value)
        else -> null
    }.also {
        addError(it)
    }

    /**
     * START LOCATION DETAILS
     */
    override suspend fun validateLocationDetails(details: String) = when {
        details.isEmpty() -> LocationDetailsError.EmptyLocationDetailsError()
        details.length > locationDetailsLength.value -> LocationDetailsError.LongLocationDetailsError(locationDetailsLength.value)
        else -> null
    }.also {
        addError(it)
    }

    /**
     * LOCATION PATH
     */
    override suspend fun validateLocationPath(path: List<Coordinate>) = when {
        path.size < pathDotsCount.value -> PathError(pathDotsCount.value)
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