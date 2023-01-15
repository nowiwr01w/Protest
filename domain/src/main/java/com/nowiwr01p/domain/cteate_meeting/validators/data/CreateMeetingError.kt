package com.nowiwr01p.domain.cteate_meeting.validators.data

import com.nowiwr01p.domain.cteate_meeting.validators.data.CustomTextFieldType.*

sealed class CreateMeetingError(
    open val type: CustomTextFieldType,
    open val errorText: String
) {

    /**
     * TOP IMAGE LINK
     */
    sealed class ImageLinkError(
        override val type: CustomTextFieldType = TOP_IMAGE,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class ExtensionError(
            override val type: CustomTextFieldType = TOP_IMAGE,
            override val errorText: String = "Ссылка должна начинаться с https://"
        ): ImageLinkError(type, errorText)

        data class ImageTypeError(
            override val type: CustomTextFieldType = TOP_IMAGE,
            override val errorText: String = "Допустимые форматы картинки: png, jpg, jpeg"
        ): ImageLinkError(type, errorText)
    }

    /**
     * CATEGORIES
     */
    data class CategoriesError(
        override val type: CustomTextFieldType = CATEGORIES,
        override val errorText: String = "Выберите хотя бы одну категорию"
    ): CreateMeetingError(type, errorText)

    /**
     * TITLE
     */
    sealed class TitleError(
        override val type: CustomTextFieldType = TITLE,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class EmptyTitleError(
            override val type: CustomTextFieldType = TITLE,
            override val errorText: String = "Заголовок не может быть пустым"
        ): TitleError(type, errorText)

        data class LongTitleError(
            override val type: CustomTextFieldType = TITLE,
            override val errorText: String = "Максимум 48 символов"
        ): TitleError(type, errorText)
    }

    /**
     * DESCRIPTION
     */
    sealed class DescriptionError(
        override val type: CustomTextFieldType = DESCRIPTION,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class EmptyDescriptionError(
            override val type: CustomTextFieldType = DESCRIPTION,
            override val errorText: String = "Описание не может быть пустым"
        ): DescriptionError(type, errorText)

        data class LongDescriptionError(
            override val type: CustomTextFieldType = DESCRIPTION,
            override val errorText: String = "Максимум 300 символов"
        ): TitleError(type, errorText)
    }

    /**
     * MEETING DATE
     */
    data class DateError(
        override val type: CustomTextFieldType = DATE,
        override val errorText: String = "Разница с текущей датой должна быть не менее 4 часов"
    ): CreateMeetingError(type, errorText)

    /**
     * START LOCATION COORDINATES
     */
    data class StartLocationError(
        override val type: CustomTextFieldType = LOCATION_COORDINATES,
        override val errorText: String = "Вы вышли за пределы допустимых локаций"
    ): CreateMeetingError(type, errorText)

    /**
     * START LOCATION TITLE
     */
    sealed class LocationTitleError(
        override val type: CustomTextFieldType = LOCATION_TITLE,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class EmptyLocationTitleError(
            override val type: CustomTextFieldType = LOCATION_TITLE,
            override val errorText: String = "Название места встречи не может быть пустым"
        ): LocationTitleError(type, errorText)

        data class LongLocationTitleError(
            override val type: CustomTextFieldType = LOCATION_TITLE,
            override val errorText: String = "Максимум 18 символов"
        ): LocationTitleError(type, errorText)
    }

    /**
     * START LOCATION DETAILS
     */
    sealed class LocationDetailsError(
        override val type: CustomTextFieldType = LOCATION_DETAILS,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class EmptyLocationDetailsError(
            override val type: CustomTextFieldType = LOCATION_DETAILS,
            override val errorText: String = "Описание места встречи не может быть пустым"
        ): LocationDetailsError(type, errorText)

        data class LongLocationDetailsError(
            override val type: CustomTextFieldType = LOCATION_DETAILS,
            override val errorText: String = "Максимум 48 символов"
        ): LocationDetailsError(type, errorText)
    }

    /**
     * LOCATION PATH
     */
    data class PathError(
        override val type: CustomTextFieldType = PATH,
        override val errorText: String = "Путь должен содержать не менее 4 точек"
    ): CreateMeetingError(type, errorText)

    /**
     * TELEGRAM LINK
     */
    sealed class TelegramLinkError(
        override val type: CustomTextFieldType = TELEGRAM,
        override val errorText: String
    ): CreateMeetingError(type, errorText) {

        data class ExtensionError(
            override val type: CustomTextFieldType = TELEGRAM,
            override val errorText: String = "Ссылка должна начинаться с https://"
        ): ImageLinkError(type, errorText)

        data class DomainError(
            override val type: CustomTextFieldType = TELEGRAM,
            override val errorText: String = "Ссылка должна вести на Telegram"
        ): ImageLinkError(type, errorText)
    }
}