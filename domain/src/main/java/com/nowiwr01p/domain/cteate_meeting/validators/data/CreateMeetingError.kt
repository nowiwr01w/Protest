package com.nowiwr01p.domain.cteate_meeting.validators.data

import com.nowiwr01p.domain.cteate_meeting.validators.data.CustomTextFieldType.*

sealed class CreateMeetingError(
    open val type: CreateMeetingFieldItemType,
    open val errorText: String
) {

    /**
     * TOP IMAGE LINK
     */
    sealed class ImageLinkError(
        override val type: CreateMeetingFieldItemType = TOP_IMAGE,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class ExtensionError(
            override val type: CreateMeetingFieldItemType = TOP_IMAGE,
            override val errorText: String = "Ссылка должна начинаться с https://"
        ): ImageLinkError(type, errorText)

        data class ImageTypeError(
            override val type: CreateMeetingFieldItemType = TOP_IMAGE,
            override val errorText: String = "Допустимые форматы картинки: png, jpg, jpeg"
        ): ImageLinkError(type, errorText)
    }

    /**
     * CATEGORIES
     */
    data class CategoriesError(
        override val type: CreateMeetingFieldItemType = CATEGORIES,
        override val errorText: String = "Выберите хотя бы одну категорию"
    ): CreateMeetingError(type, errorText)

    /**
     * TITLE
     */
    sealed class TitleError(
        override val type: CreateMeetingFieldItemType = TITLE,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class EmptyTitleError(
            override val type: CreateMeetingFieldItemType = TITLE,
            override val errorText: String = "Заголовок не может быть пустым"
        ): TitleError(type, errorText)

        data class LongTitleError(
            override val type: CreateMeetingFieldItemType = TITLE,
            override val errorText: String = "Максимум 48 символов"
        ): TitleError(type, errorText)
    }

    /**
     * DESCRIPTION
     */
    sealed class DescriptionError(
        override val type: CreateMeetingFieldItemType = DESCRIPTION,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class EmptyDescriptionError(
            override val type: CreateMeetingFieldItemType = DESCRIPTION,
            override val errorText: String = "Описание не может быть пустым"
        ): DescriptionError(type, errorText)

        data class LongDescriptionError(
            override val type: CreateMeetingFieldItemType = DESCRIPTION,
            override val errorText: String = "Максимум 300 символов"
        ): TitleError(type, errorText)
    }

    /**
     * MEETING DATE
     */
    data class DateError(
        override val type: CreateMeetingFieldItemType = DATE,
        override val errorText: String = "Разница с текущей датой должна быть не менее 4 часов"
    ): CreateMeetingError(type, errorText)

    /**
     * START LOCATION COORDINATES
     */
    data class StartLocationError(
        override val type: CreateMeetingFieldItemType = LOCATION_COORDINATES,
        override val errorText: String = "Вы вышли за пределы допустимых локаций"
    ): CreateMeetingError(type, errorText)

    /**
     * START LOCATION TITLE
     */
    sealed class LocationTitleError(
        override val type: CreateMeetingFieldItemType = LOCATION_TITLE,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class EmptyLocationTitleError(
            override val type: CreateMeetingFieldItemType = LOCATION_TITLE,
            override val errorText: String = "Название места встречи не может быть пустым"
        ): LocationTitleError(type, errorText)

        data class LongLocationTitleError(
            override val type: CreateMeetingFieldItemType = LOCATION_TITLE,
            override val errorText: String = "Максимум 18 символов"
        ): LocationTitleError(type, errorText)
    }

    /**
     * START LOCATION DETAILS
     */
    sealed class LocationDetailsError(
        override val type: CreateMeetingFieldItemType = LOCATION_DETAILS,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class EmptyLocationDetailsError(
            override val type: CreateMeetingFieldItemType = LOCATION_DETAILS,
            override val errorText: String = "Описание места встречи не может быть пустым"
        ): LocationDetailsError(type, errorText)

        data class LongLocationDetailsError(
            override val type: CreateMeetingFieldItemType = LOCATION_DETAILS,
            override val errorText: String = "Максимум 48 символов"
        ): LocationDetailsError(type, errorText)
    }

    /**
     * LOCATION PATH
     */
    data class PathError(
        override val type: CreateMeetingFieldItemType = PATH,
        override val errorText: String = "Путь должен содержать не менее 4 точек"
    ): CreateMeetingError(type, errorText)

    /**
     * TELEGRAM LINK
     */
    sealed class TelegramLinkError(
        override val type: CreateMeetingFieldItemType = TELEGRAM,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class ExtensionError(
            override val type: CreateMeetingFieldItemType = TELEGRAM,
            override val errorText: String = "Ссылка должна начинаться с https://"
        ): ImageLinkError(type, errorText)

        data class DomainError(
            override val type: CreateMeetingFieldItemType = TELEGRAM,
            override val errorText: String = "Ссылка должна вести на Telegram"
        ): ImageLinkError(type, errorText)
    }
}