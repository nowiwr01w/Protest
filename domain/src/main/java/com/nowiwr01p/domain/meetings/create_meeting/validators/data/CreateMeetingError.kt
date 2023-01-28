package com.nowiwr01p.domain.meetings.create_meeting.validators.data

import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CustomTextFieldType.*

sealed class CreateMeetingError(
    open val errorText: String,
    open val type: CreateMeetingFieldItemType,
    open val priority: Int,
) {

    /**
     * TOP IMAGE LINK
     */
    sealed class ImageLinkError(
        override val errorText: String,
        override val type: CreateMeetingFieldItemType = TOP_IMAGE,
        override val priority: Int = 1,
    ): CreateMeetingError(errorText, type, priority) {

        data class ExtensionError(
            override val errorText: String = "Ссылка должна начинаться с https://"
        ): ImageLinkError(errorText)

        data class ImageTypeError(
            override val errorText: String = "Допустимые форматы картинки: png, jpg, jpeg, webp"
        ): ImageLinkError(errorText)
    }

    /**
     * CATEGORIES
     */
    data class CategoriesError(
        override val errorText: String = "Выберите хотя бы одну категорию",
        override val type: CreateMeetingFieldItemType = CATEGORIES,
        override val priority: Int = 2
    ): CreateMeetingError(errorText, type, priority)

    /**
     * TITLE
     */
    sealed class TitleError(
        override val errorText: String,
        override val type: CreateMeetingFieldItemType = TITLE,
        override val priority: Int = 3,
    ): CreateMeetingError(errorText, type, priority) {

        data class EmptyTitleError(
            override val errorText: String = "Заголовок не может быть пустым"
        ): TitleError(errorText)

        data class LongTitleError(
            val titleLength: Int,
            override val errorText: String = "Максимум $titleLength символов"
        ): TitleError(errorText)
    }

    /**
     * DESCRIPTION
     */
    sealed class DescriptionError(
        override val errorText: String,
        override val type: CreateMeetingFieldItemType = DESCRIPTION,
        override val priority: Int = 4,
    ): CreateMeetingError(errorText, type, priority) {

        data class EmptyDescriptionError(
            override val errorText: String = "Описание не может быть пустым"
        ): DescriptionError(errorText)

        data class LongDescriptionError(
            val textLength: Int,
            override val errorText: String = "Максимум $textLength символов"
        ): TitleError(errorText)
    }

    /**
     * MEETING DATE
     */
    sealed class DateError(
        override val errorText: String,
        override val type: CreateMeetingFieldItemType = DATE,
        override val priority: Int = 5,
    ): CreateMeetingError(errorText, type, priority) {

        data class DateNotSelectedError(
            override val errorText: String = "У митинга должна быть дата"
        ): DateError(errorText)

        data class DateBeforeError(
            override val errorText: String = "Эта дата уже прошла"
        ): DateError(errorText)

        data class EarlyDateError(
            val hoursDifference: Int,
            override val errorText: String = "Разница с текущей датой должна быть не менее $hoursDifference часов"
        ): DateError(errorText)
    }

    /**
     * START LOCATION COORDINATES
     */
    data class StartLocationError(
        override val errorText: String = "Вы вышли за пределы допустимых локаций",
        override val priority: Int = 6,
        override val type: CreateMeetingFieldItemType = LOCATION_COORDINATES,
    ): CreateMeetingError(errorText, type, priority)

    /**
     * START LOCATION TITLE
     */
    sealed class LocationTitleError(
        override val errorText: String,
        override val type: CreateMeetingFieldItemType = LOCATION_TITLE,
        override val priority: Int = 7,
    ): CreateMeetingError(errorText, type, priority) {

        data class EmptyLocationTitleError(
            override val errorText: String = "Название места встречи не может быть пустым"
        ): LocationTitleError(errorText)

        data class LongLocationTitleError(
            val locationPlaceLength: Int,
            override val errorText: String = "Максимум $locationPlaceLength символов"
        ): LocationTitleError(errorText)
    }

    /**
     * START LOCATION DETAILS
     */
    sealed class LocationDetailsError(
        override val errorText: String,
        override val type: CreateMeetingFieldItemType = LOCATION_DETAILS,
        override val priority: Int = 8,
    ): CreateMeetingError(errorText, type, priority) {

        data class EmptyLocationDetailsError(
            override val errorText: String = "Описание места встречи не может быть пустым"
        ): LocationDetailsError(errorText)

        data class LongLocationDetailsError(
            val locationDetailsLength: Int,
            override val errorText: String = "Максимум $locationDetailsLength символов"
        ): LocationDetailsError(errorText)
    }

    /**
     * LOCATION PATH
     */
    data class PathError(
        val pathDotsCount: Int,
        override val errorText: String = "Путь должен содержать не менее $pathDotsCount точек",
        override val priority: Int = 9,
        override val type: CreateMeetingFieldItemType = PATH,
    ): CreateMeetingError(errorText, type, priority)

    /**
     * TELEGRAM LINK
     */
    sealed class TelegramLinkError(
        override val errorText: String,
        override val type: CreateMeetingFieldItemType = TELEGRAM,
        override val priority: Int = 10,
    ): CreateMeetingError(errorText, type, priority) {

        data class ExtensionError(
            override val errorText: String = "Ссылка должна начинаться с https://"
        ): TelegramLinkError(errorText)

        data class DomainError(
            override val errorText: String = "Ссылка должна вести на Telegram"
        ): TelegramLinkError(errorText)
    }
}