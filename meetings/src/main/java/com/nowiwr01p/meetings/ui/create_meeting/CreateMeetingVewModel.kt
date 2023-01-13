package com.nowiwr01p.meetings.ui.create_meeting

import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.cteate_meeting.GetCachedCategoriesUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.*
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType.*

class CreateMeetingVewModel(
    private val getCachedCategoriesUseCase: GetCachedCategoriesUseCase,
    private val showBottomSheetHelper: ShowBottomSheetHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.OnAddDetailsItemClick -> changeDetailsListState(event.type)
            is Event.OnRemoveDetailsItemClick -> changeDetailsListState(event.type, event.index)
            is Event.OnSelectedCategoryClick -> selectCategory(event.category)
            is Event.ShowCategoriesBottomSheet -> showBottomSheetHelper.showBottomSheet(event.content)
            is Event.NavigateToMapDrawPath -> setEffect { Effect.NavigateToMapDrawPath }
            is Event.NavigateToChooseStartLocation -> setEffect { Effect.NavigateToChooseStartLocation }
            is Event.ShowDateTimePicker -> showDateTimePicker()
            is Event.SelectDate -> selectDate(event.date)
            is Event.SelectTime -> selectTime(event.time)
            is Event.SetDrawnPath -> setPath(event.path)
            is Event.SetStartLocationPath -> setStartLocation(event.position)
        }
    }

    private fun init() = io {
        runCatching {
            getCategories()
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
    private fun changeDetailsListState(type: DetailsItemType, index: Int = -1) = with(viewState.value) {
        val list = when (type) {
            GOALS -> goals
            SLOGANS -> slogans
            STRATEGY -> strategy
            POSTER_LINKS -> posters
        }
        val updated = list.toMutableList().apply {
            if (index == -1) add("") else removeAt(index)
        }
        updateDetailsList(type, updated)
    }

    private fun updateDetailsList(type: DetailsItemType, list: List<String>) = setState {
        when (type) {
            GOALS -> copy(goals = list)
            SLOGANS -> copy(slogans = list)
            STRATEGY -> copy(strategy = list)
            POSTER_LINKS -> copy(posters = list)
        }
    }

    /**
     * DATE TIME PICKER
     */
    private fun showDateTimePicker() {
        setState { copy(showDatePicker = true) }
    }

    private fun selectDate(date: String) = setState {
        copy(selectedDate = date, showDatePicker = false, showTimePicker = true)
    }

    private fun selectTime(time: String) = setState {
        copy(selectedTime = time, showTimePicker = false)
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
}