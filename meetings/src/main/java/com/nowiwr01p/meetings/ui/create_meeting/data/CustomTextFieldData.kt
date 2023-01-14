package com.nowiwr01p.meetings.ui.create_meeting.data

import androidx.compose.ui.text.input.KeyboardType
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.*
import com.nowiwr01p.meetings.ui.create_meeting.data.CustomTextFieldType.*

/**
 * LISTENER REQUIRED HERE BECAUSE WE HAVE DYNAMIC ITEMS - ExpandableItems
 */
open class CustomTextFieldData(
    open val type: CustomTextFieldType,
    open val value: String,
    open val hint: String,
    open val keyboardType: KeyboardType = KeyboardType.Text,
    open val showSubItemSlash: Boolean = false,
    open val onValueChanged: (String) -> Unit = { },
    open val trailingIconCallback: (() -> Unit)? = null
) {
    data class TopImageItem(
        val state: State,
        val listener: Listener?,
        override val type: CustomTextFieldType = TOP_IMAGE,
        override val value: String = state.imageLink,
        override val hint: String = "Ссылка на картинку",
        override val onValueChanged: (String) -> Unit = { listener?.onEditCustomTextField(type, it) }
    ): CustomTextFieldData(type, value, hint)

    data class TitleItem(
        val state: State,
        val listener: Listener?,
        override val type: CustomTextFieldType = TITLE,
        override val value: String = state.title,
        override val hint: String = "Название",
        override val onValueChanged: (String) -> Unit = { listener?.onEditCustomTextField(type, it) }
    ): CustomTextFieldData(type, value, hint)

    data class DescriptionItem(
        val state: State,
        val listener: Listener?,
        override val type: CustomTextFieldType = DESCRIPTION,
        override val value: String = state.description,
        override val hint: String = "Описание",
        override val onValueChanged: (String) -> Unit = { listener?.onEditCustomTextField(type, it) }
    ): CustomTextFieldData(type, value, hint)

    data class OpenDateItem(
        val state: State,
        val listener: Listener?,
        override val type: CustomTextFieldType = OPEN_DATE,
        override val value: String = state.requiresPeopleCount,
        override val hint: String = "Необходимое количество человек",
        override val keyboardType: KeyboardType = KeyboardType.Number,
        override  val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = { listener?.onEditCustomTextField(type, it) }
    ): CustomTextFieldData(type, value, hint, keyboardType, showSubItemSlash)

    data class TelegramItem(
        val state: State,
        val listener: Listener?,
        override val type: CustomTextFieldType = TELEGRAM,
        override val value: String = state.telegram,
        override val hint: String = "Telegram",
        override val onValueChanged: (String) -> Unit = { listener?.onEditCustomTextField(type, it) }
    ): CustomTextFieldData(type, value, hint)

    data class PosterMotivationItem(
        val state: State,
        val listener: Listener?,
        override val type: CustomTextFieldType = POSTER_MOTIVATION,
        override val value: String = state.postersMotivation,
        override val hint: String = "Мотивация идти с плакатами",
        override val onValueChanged: (String) -> Unit = { listener?.onEditCustomTextField(type, it) }
    ): CustomTextFieldData(type, value, hint)

    data class LocationItem(
        val state: State,
        val listener: Listener?,
        override val type: CustomTextFieldType = LOCATION,
        override val value: String = state.location,
        override val hint: String = "Название места встречи",
        override  val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = { listener?.onEditCustomTextField(type, it) }
    ): CustomTextFieldData(type, value, hint)

    data class LocationDetailsItem(
        val state: State,
        val listener: Listener?,
        override val type: CustomTextFieldType = LOCATION_DETAILS,
        override val value: String = state.locationDetails,
        override val hint: String = "Детали места встречи",
        override  val showSubItemSlash: Boolean = true,
        override val onValueChanged: (String) -> Unit = { listener?.onEditCustomTextField(type, it) }
    ): CustomTextFieldData(type, value, hint)
}