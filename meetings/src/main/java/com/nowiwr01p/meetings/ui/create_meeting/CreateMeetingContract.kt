package com.nowiwr01p.meetings.ui.create_meeting

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.meetings.ui.create_meeting.data.CheckBoxType
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType

interface CreateMeetingContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        data class OnAddDetailsItemClick(val type: DetailsItemType): Event
        data class OnRemoveDetailsItemClick(val type: DetailsItemType, val index: Int): Event
        data class SetCheckBoxState(val type: CheckBoxType, val value: Boolean): Event
    }

    data class State(
        val posters: List<String> = listOf(),
        val goals: List<String> = listOf(),
        val slogans: List<String> = listOf(),
        val strategy: List<String> = listOf(),
        val categories: List<Category> = listOf(),
        val selectedCategories: List<Category> = listOf(),
        val isDateCheckBoxChecked: Boolean = false,
        val isOpenDateCheckBoxChecked: Boolean = false
    ): ViewState

    sealed interface Effect: ViewSideEffect

    interface Listener {
        fun onBackClick()
        fun setCheckBoxState(type: CheckBoxType, value: Boolean)
        fun onAddDetailsItem(type: DetailsItemType)
        fun onRemoveDetailsType(type: DetailsItemType, index: Int)
    }
}