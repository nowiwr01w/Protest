package com.nowiwr01p.meetings.ui.create_meeting

import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.ui.date_time_picker.DatePickerListener
import com.nowiwr01p.core_ui.ui.date_time_picker.TimePickerListener
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CreateMeetingError
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CreateMeetingFieldItemType
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.DetailsFieldType
import com.nowiwr01p.meetings.ui.create_meeting.data.BottomSheetType

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
        data class SetMeetingEverywhere(val everywhere: Boolean): Event
        data class ShowBottomSheet(val params: BottomSheetParams): Event
        data class OnSelectedCategoryClick(val category: Category): Event
        data class OnAddDetailsItemClick(val type: DetailsFieldType): Event
        data class OnRemoveDetailsItemClick(val type: DetailsFieldType, val index: Int): Event
        data class OnEditCustomTextField(val type: CreateMeetingFieldItemType, val value: String): Event
        data class OnEditDetailsItemClick(val type: DetailsFieldType, val index: Int, val value: String): Event
    }

    data class State(
        val user: User = User(),
        val meetingEverywhere: Boolean = false,
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
        val validationErrors: List<CreateMeetingError> = listOf()
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object NavigateToMapDrawPath: Effect
        object NavigateToChooseStartLocation: Effect
        data class NavigateToPreview(val meeting: Meeting): Effect
    }

    interface Listener: DatePickerListener, TimePickerListener {
        fun onBackClick()
        fun onAddDetailsItem(type: DetailsFieldType)
        fun onRemoveDetailsItem(type: DetailsFieldType, index: Int)
        fun onEditDetailsItem(type: DetailsFieldType, index: Int, value: String)
        fun showBottomSheet(type: BottomSheetType)
        fun showDateTimePicker()
        fun navigateToMapDrawPath()
        fun navigateChooseStartLocation()
        fun onEditCustomTextField(type: CreateMeetingFieldItemType, value: String)
        fun navigateToPreview()
    }
}