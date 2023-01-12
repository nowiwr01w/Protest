package com.nowiwr01p.meetings.ui.create_meeting

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.meetings.ui.create_meeting.data.CheckBoxType

interface CreateMeetingContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        data class SetCheckBoxState(val type: CheckBoxType, val value: Boolean): Event
    }

    data class State(
        val categories: List<Category> = listOf(),
        val isDateCheckBoxChecked: Boolean = false,
        val isPostersCheckBoxChecked: Boolean = false,
        val isOpenDateCheckBoxChecked: Boolean = false,
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {
        fun onBackClick()
        fun setCheckBoxState(type: CheckBoxType, value: Boolean)
    }
}