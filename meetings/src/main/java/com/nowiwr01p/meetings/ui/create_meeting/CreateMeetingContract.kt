package com.nowiwr01p.meetings.ui.create_meeting

import androidx.compose.runtime.Composable
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType

interface CreateMeetingContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object ShowDateTimePicker: Event
        object NavigateToMapDrawPath: Event
        object NavigateToChooseStartLocation: Event
        data class ShowCategoriesBottomSheet(val content: @Composable () -> Unit): Event
        data class OnSelectedCategoryClick(val category: Category): Event
        data class OnAddDetailsItemClick(val type: DetailsItemType): Event
        data class OnRemoveDetailsItemClick(val type: DetailsItemType, val index: Int): Event
    }

    data class State(
        val posters: List<String> = listOf(),
        val goals: List<String> = listOf(),
        val slogans: List<String> = listOf(),
        val strategy: List<String> = listOf(),
        val categories: List<Category> = listOf(),
        val selectedCategories: Set<Category> = setOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateToMapDrawPath: Effect
        object NavigateToChooseStartLocation: Effect
    }

    interface Listener {
        fun onBackClick()
        fun onAddDetailsItem(type: DetailsItemType)
        fun onRemoveDetailsType(type: DetailsItemType, index: Int)
        fun showCategoriesBottomSheet()
        fun showDateTimePicker()
        fun navigateToMapDrawPath()
        fun navigateChooseStartLocation()
    }
}