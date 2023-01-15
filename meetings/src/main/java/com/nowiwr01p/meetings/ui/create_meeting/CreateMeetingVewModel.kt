package com.nowiwr01p.meetings.ui.create_meeting

import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.datastore.cities.data.*
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.core_ui.ui.snack_bar.SnackBarParams
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.cteate_meeting.usecase.GetCachedCategoriesUseCase
import com.nowiwr01p.domain.cteate_meeting.usecase.ValidateMeetingDataUseCase
import com.nowiwr01p.domain.cteate_meeting.validators.data.CreateMeetingError
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.*
import com.nowiwr01p.domain.cteate_meeting.validators.data.CreateMeetingFieldItemType
import com.nowiwr01p.domain.cteate_meeting.validators.data.CustomTextFieldType.*
import com.nowiwr01p.domain.cteate_meeting.validators.data.DetailsFieldType
import com.nowiwr01p.domain.cteate_meeting.validators.data.DetailsFieldType.*

class CreateMeetingVewModel(
    private val statusBarColor: Color,
    private val getCachedCategoriesUseCase: GetCachedCategoriesUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val validateMeetingDataUseCase: ValidateMeetingDataUseCase,
    private val showBottomSheetHelper: ShowBottomSheetHelper,
    private val showSnackBarHelper: ShowSnackBarHelper,
    private val mapper: CreateMeetingMapper
): BaseViewModel<Event, State, Effect>() {

    init {
        mapper.viewModel = this
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.OnAddDetailsItemClick -> addRemoveDetailsItem(event.type)
            is Event.OnEditDetailsItemClick -> editDetailsItem(event.type, event.index, event.value)
            is Event.OnRemoveDetailsItemClick -> addRemoveDetailsItem(event.type, event.index)
            is Event.OnEditCustomTextField -> editCustomTextField(event.type, event.value)
            is Event.OnSelectedCategoryClick -> selectCategory(event.category)
            is Event.ShowCategoriesBottomSheet -> showBottomSheetHelper.showBottomSheet(event.params)
            is Event.NavigateToMapDrawPath -> setEffect { Effect.NavigateToMapDrawPath }
            is Event.NavigateToChooseStartLocation -> setEffect { Effect.NavigateToChooseStartLocation }
            is Event.ShowDateTimePicker -> showDateTimePicker()
            is Event.SelectDate -> selectDate(event.date)
            is Event.SelectTime -> selectTime(event.time)
            is Event.SetDrawnPath -> setPath(event.path)
            is Event.SetStartLocationPath -> setStartLocation(event.position)
            is Event.NavigateToPreview -> validateMeetingData()
        }
    }

    private fun init() = io {
        runCatching {
            getCategories()
            getUserData()
        }
    }

    /**
     * CATEGORIES
     */
    private suspend fun getCategories() {
        val categories = getCachedCategoriesUseCase.execute()
        setState { copy(categories = categories) }
    }

    /**
     * USER
     */
    private suspend fun getUserData() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    /**
     * SELECTED CATEGORIES
     */
    private fun selectCategory(category: Category) = with(viewState.value) {
        val updated = selectedCategories.toMutableSet().apply {
            if (selectedCategories.contains(category)) remove(category) else add(category)
        }
        setState { copy(selectedCategories = updated) }
    }

    /**
     * DETAILS (POSTER LINKS, GOALS, SLOGANS, STRATEGY)
     */
    private fun addRemoveDetailsItem(type: DetailsFieldType, index: Int = -1) = with(viewState.value) {
        val updated = getDetailsList(type).toMutableList().apply {
            if (index == -1) add("") else removeAt(index)
        }
        updateDetailsList(type, updated)
    }

    private fun editDetailsItem(type: DetailsFieldType, index: Int, value: String) = with(viewState.value) {
        val updated = getDetailsList(type).mapIndexed { curIndex, item ->
            if (index == curIndex) value else item
        }
        updateDetailsList(type, updated)
    }

    private fun getDetailsList(type: DetailsFieldType) = with(viewState.value) {
        when (type) {
            GOALS -> goals
            SLOGANS -> slogans
            STRATEGY -> strategy
            POSTER_LINKS -> posters
        }
    }

    private fun updateDetailsList(type: DetailsFieldType, list: List<String>) = setState {
        when (type) {
            GOALS -> copy(goals = list)
            SLOGANS -> copy(slogans = list)
            STRATEGY -> copy(strategy = list)
            POSTER_LINKS -> copy(posters = list)
        }
    }

    /**
     * CUSTOM TEXT FIELD (IMAGE, TITLE, DESCRIPTION, OPEN DATE, LOCATION...)
     */
    private fun editCustomTextField(type: CreateMeetingFieldItemType, value: String) = setState {
        when (type) {
            TOP_IMAGE -> copy(imageLink = value)
            TITLE -> copy(title = value)
            DESCRIPTION -> copy(description = value)
            OPEN_DATE -> copy(requiresPeopleCount = value)
            TELEGRAM -> copy(telegram = value)
            POSTER_MOTIVATION -> copy(postersMotivation = value)
            LOCATION_TITLE -> copy(location = value)
            else -> copy(locationDetails = value)
        }
    }

    /**
     * DATE TIME PICKER
     */
    private fun showDateTimePicker() {
        setState { copy(showDatePicker = true) }
    }

    private fun selectDate(date: Long) = setState {
        copy(selectedDate = date, showDatePicker = false, showTimePicker = true)
    }

    private fun selectTime(time: Long) = setState {
        copy(selectedDate = viewState.value.selectedDate + time, showTimePicker = false)
    }

    /**
     * LOCATION
     */
    private fun setPath(path: List<LatLng>) = setState {
        copy(path = path)
    }

    private fun setStartLocation(position: LatLng) = setState {
        copy(startLocation = position)
    }

    /**
     * VALIDATE MEETING DATA
     */
    private fun validateMeetingData() = io {
        val meeting = mapper.getMeeting()
        runCatching {
            validateMeetingDataUseCase.execute(meeting)
        }.onSuccess {
            onDataValidated(meeting, it)
        }
    }

    private fun onDataValidated(meeting: Meeting, errors: List<CreateMeetingError>) {
        if (errors.isEmpty()) {
            setEffect { Effect.NavigateToPreview(meeting) }
        } else {
            val params = SnackBarParams(
                fromStatusBarColor = statusBarColor,
                text = errors.sortedBy { it.priority }.first().errorText
            )
            showSnackBarHelper.showErrorSnackBar(params)
            setState { copy(validationErrors = errors) }
        }
    }
}