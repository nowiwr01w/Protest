package com.nowiwr01p.meetings.ui.main

import androidx.compose.runtime.Composable
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.core.model.Category

interface MeetingsContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        data class SelectCategory(val category: Category): Event
        data class ShowBottomSheet(val content: @Composable () -> Unit): Event
    }

    data class State(
        val showProgress: Boolean = false,
        val user: User = User(),
        val meetings: List<Meeting> = listOf(),
        val categories: List<Category> = listOf(),
        val selectedCategory: Category = Category()
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {
        fun toMap(meeting: Meeting)
        fun toMeeting(meeting: Meeting)
        fun toCreateMeeting()
        fun showBecomeOrganizerBottomSheet()
        fun onCategoryClick(category: Category)
    }
}