package com.nowiwr01p.meetings.ui.create_meeting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.extensions.DatePickerListener
import com.nowiwr01p.core_ui.extensions.TimePickerListener
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
        data class SelectDate(val date: String): Event
        data class SelectTime(val time: String): Event
        data class SetDrawnPath(val path: List<LatLng>): Event
        data class SetStartLocationPath(val position: LatLng): Event
        data class ShowCategoriesBottomSheet(val content: @Composable () -> Unit): Event
        data class OnSelectedCategoryClick(val category: Category): Event
        data class OnAddDetailsItemClick(val type: DetailsItemType): Event
        data class OnRemoveDetailsItemClick(val type: DetailsItemType, val index: Int): Event
        data class OnEditDetailsItemClick(val type: DetailsItemType, val index: Int, val value: String): Event
    }

    data class State(
        val posters: List<String> = mutableStateListOf(),
        val goals: List<String> = mutableStateListOf(),
        val slogans: List<String> = mutableStateListOf(),
        val strategy: List<String> = mutableStateListOf(),
        val categories: List<Category> = listOf(),
        val selectedCategories: Set<Category> = setOf(),
        val showDatePicker: Boolean = false,
        val showTimePicker: Boolean = false,
        val selectedDate: String = "",
        val selectedTime: String = "",
        val path: List<LatLng> = listOf(),
        val startLocation: LatLng = LatLng(.0, .0)
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateToMapDrawPath: Effect
        object NavigateToChooseStartLocation: Effect
    }

    interface Listener: DatePickerListener, TimePickerListener {
        fun onBackClick()
        fun onAddDetailsItem(type: DetailsItemType)
        fun onRemoveDetailsItem(type: DetailsItemType, index: Int)
        fun onEditDetailsItem(type: DetailsItemType, index: Int, value: String)
        fun showCategoriesBottomSheet()
        fun showDateTimePicker()
        fun navigateToMapDrawPath()
        fun navigateChooseStartLocation()
    }
}