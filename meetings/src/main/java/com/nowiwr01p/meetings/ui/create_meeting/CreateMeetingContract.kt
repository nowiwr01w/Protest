package com.nowiwr01p.meetings.ui.create_meeting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.extensions.DatePickerListener
import com.nowiwr01p.core_ui.extensions.TimePickerListener
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.domain.cteate_meeting.validators.data.CreateMeetingError
import com.nowiwr01p.domain.cteate_meeting.validators.data.CustomTextFieldType
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType

interface CreateMeetingContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object ShowDateTimePicker: Event
        object NavigateToPreview: Event
        object NavigateToMapDrawPath: Event
        object NavigateToChooseStartLocation: Event
        data class SelectDate(val date: Long): Event
        data class SelectTime(val time: Long): Event
        data class SetDrawnPath(val path: List<LatLng>): Event
        data class SetStartLocationPath(val position: LatLng): Event
        data class ShowCategoriesBottomSheet(val content: @Composable () -> Unit): Event
        data class OnSelectedCategoryClick(val category: Category): Event
        data class OnAddDetailsItemClick(val type: DetailsItemType): Event
        data class OnRemoveDetailsItemClick(val type: DetailsItemType, val index: Int): Event
        data class OnEditCustomTextField(val type: CustomTextFieldType, val value: String): Event
        data class OnEditDetailsItemClick(val type: DetailsItemType, val index: Int, val value: String): Event
    }

    data class State(
        val user: User = User(),
        val imageLink: String = "",
        val categories: List<Category> = listOf(),
        val selectedCategories: Set<Category> = setOf(),
        val title: String = "",
        val description: String = "",
        val showDatePicker: Boolean = false,
        val showTimePicker: Boolean = false,
        val selectedDate: Long = 0L,
        val startLocation: LatLng = LatLng(.0, .0),
        val location: String = "",
        val locationDetails: String = "",
        val path: List<LatLng> = listOf(),
        val requiresPeopleCount: String = "",
        val telegram: String = "",
        val postersMotivation: String = "",
        val posters: List<String> = mutableStateListOf(),
        val goals: List<String> = mutableStateListOf(),
        val slogans: List<String> = mutableStateListOf(),
        val strategy: List<String> = mutableStateListOf(),
        val validationErrors: List<CreateMeetingError?> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateToMapDrawPath: Effect
        object NavigateToChooseStartLocation: Effect
        data class NavigateToPreview(val meeting: Meeting): Effect
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
        fun onEditCustomTextField(type: CustomTextFieldType, value: String)
        fun navigateToPreview()
    }
}