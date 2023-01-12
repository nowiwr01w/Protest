package com.nowiwr01p.meetings.ui.create_meeting

import com.nowiwr01p.core.datastore.location.data.Poster
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.cteate_meeting.GetCachedCategoriesUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.*
import com.nowiwr01p.meetings.ui.create_meeting.data.CheckBoxType
import com.nowiwr01p.meetings.ui.create_meeting.data.CheckBoxType.*
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType.*

class CreateMeetingVewModel(
    private val getCachedCategoriesUseCase: GetCachedCategoriesUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.SetCheckBoxState -> setCheckBoxState(event.type, event.value)
            is Event.OnAddPosterClick -> addPosterField()
            is Event.OnRemovePosterClick -> removePosterField(event.index)
            is Event.OnAddDetailsItemClick -> changeDetailsListState(event.type)
            is Event.OnRemoveDetailsItemClick -> changeDetailsListState(event.type, event.index)
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
     * CHECKBOX STATE
     */
    private fun setCheckBoxState(type: CheckBoxType, value: Boolean) = setState {
        when (type) {
            DATE -> copy(isDateCheckBoxChecked = value)
            OPEN_DATE -> copy(isOpenDateCheckBoxChecked = value)
        }
    }

    /**
     * POSTERS
     */
    private fun addPosterField() = with(viewState.value) {
        val updated = posters.toMutableList().apply { add(Poster()) }
        setState { copy(posters = updated) }
    }

    private fun removePosterField(index: Int) = with(viewState.value) {
        val updated = posters.toMutableList().apply { removeAt(index) }
        setState { copy(posters = updated) }
    }

    /**
     * DETAILS (GOALS, SLOGANS, STRATEGY)
     */
    private fun changeDetailsListState(type: DetailsItemType, index: Int = -1) = with(viewState.value) {
        val list = when (type) {
            GOALS -> goals
            SLOGANS -> slogans
            STRATEGY -> strategy
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
        }
    }
}