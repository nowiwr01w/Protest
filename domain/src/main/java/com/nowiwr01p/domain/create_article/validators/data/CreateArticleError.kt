package com.nowiwr01p.domain.create_article.validators.data

import com.nowiwr01p.domain.create_article.validators.data.DynamicFields.*
import com.nowiwr01p.domain.create_article.validators.data.StaticFields.*

sealed class CreateArticleError(
    open val errorText: String,
    open val type: CreateArticleFieldType,
    open val contentIndex: Int = 0
) {
    /**
     * TOP IMAGE
     */
    sealed class TopImageError(
        override val errorText: String,
        override val type: StaticFields = TOP_IMAGE_FIELD,
    ): CreateArticleError(errorText, type) {

        data class ExtensionError(
            override val errorText: String = "Ссылка должна начинаться с https://"
        ): TopImageError(errorText)

        data class ImageTypeError(
            override val errorText: String = "Допустимые форматы картинки: png, jpg, jpeg"
        ): TopImageError(errorText)
    }

    /**
     * TITLE
     */
    sealed class TitleError(
        override val errorText: String,
        override val type: CreateArticleFieldType = TITLE_FIELD,
        override val contentIndex: Int = 1
    ): CreateArticleError(errorText, type, contentIndex) {

        data class EmptyTitleError(
            override val errorText: String = "Заголовок не может быть пустым"
        ): TitleError(errorText)

        data class LongTitleError(
            override val errorText: String = "Максимум 48 символов"
        ): TitleError(errorText)
    }

    /**
     * TITLE
     */
    sealed class DescriptionError(
        override val errorText: String,
        override val type: CreateArticleFieldType = DESCRIPTION_FIELD,
        override val contentIndex: Int = 1
    ): CreateArticleError(errorText, type, contentIndex) {

        data class EmptyDescriptionError(
            override val errorText: String = "Описание не может быть пустым"
        ): DescriptionError(errorText)

        data class LongDescriptionError(
            override val errorText: String = "Максимум 300 символов"
        ): DescriptionError(errorText)
    }

    /**
     * SUBTITLE
     */
    sealed class SubTitleError(
        override val errorText: String,
        override val type: DynamicFields = SUBTITLE_FIELD,
        override val contentIndex: Int
    ): CreateArticleError(errorText, type, contentIndex) {

        data class EmptySubTitleError(
            override val errorText: String = "Подзаголовок не может быть пустым",
            override val contentIndex: Int
        ): SubTitleError(errorText = errorText, contentIndex = contentIndex)

        data class LongSubTitleError(
            override val errorText: String = "Максимум 48 символов",
            override val contentIndex: Int
        ): SubTitleError(errorText = errorText, contentIndex = contentIndex)
    }

    /**
     * TEXT
     */
    sealed class TextError(
        override val errorText: String,
        override val type: DynamicFields = TEXT_FIELD,
        override val contentIndex: Int
    ): CreateArticleError(errorText, type, contentIndex) {

        data class EmptyTextError(
            override val errorText: String = "Текст не может быть пустым",
            override val contentIndex: Int
        ): TextError(errorText = errorText, contentIndex = contentIndex)

        data class LongTextError(
            override val errorText: String = "Максимум 300 символов",
            override val contentIndex: Int
        ): TextError(errorText = errorText, contentIndex = contentIndex)
    }

    /**
     * IMAGE LIST
     */
    sealed class ImageListError(
        override val errorText: String,
        override val type: DynamicFields,
        override val contentIndex: Int,
    ): CreateArticleError(errorText, type, contentIndex) {

        data class ExtensionError(
            override val errorText: String = "Ссылка должна начинаться с https://",
            override val type: DynamicFields = IMAGE_LINK,
            override val contentIndex: Int
        ): ImageListError(errorText, type, contentIndex)

        data class ImageTypeError(
            override val errorText: String = "Допустимые форматы картинки: png, jpg, jpeg",
            override val type: DynamicFields = IMAGE_LINK,
            override val contentIndex: Int
        ): ImageListError(errorText, type, contentIndex)
    }

    /**
     * IMAGE LIST
     */
    sealed class OrderedListError(
        override val errorText: String,
        override val type: DynamicFields,
        override val contentIndex: Int,
    ): CreateArticleError(errorText, type, contentIndex) {

        data class EmptyTitleError(
            override val errorText: String = "Заголовок листа не может быть пустым",
            override val type: DynamicFields = ORDERED_LIST_TITLE,
            override val contentIndex: Int
        ): OrderedListError(errorText, type, contentIndex)

        data class EmptyStepItemError(
            override val errorText: String = "Элемент листа не может быть пустым",
            override val type: DynamicFields = ORDERED_LIST_STEP,
            override val contentIndex: Int
        ): OrderedListError(errorText, type, contentIndex)
    }
}