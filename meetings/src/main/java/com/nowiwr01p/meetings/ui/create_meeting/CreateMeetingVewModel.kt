package com.nowiwr01p.meetings.ui.create_meeting

import com.nowiwr01p.core.datastore.location.data.Poster
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.cteate_meeting.GetCachedCategoriesUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.*
import com.nowiwr01p.meetings.ui.create_meeting.data.CheckBoxType
import com.nowiwr01p.meetings.ui.create_meeting.data.CheckBoxType.*

class CreateMeetingVewModel(
    private val getCachedCategoriesUseCase: GetCachedCategoriesUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.SetCheckBoxState -> setCheckBoxState(event.type, event.value)
            is Event.OnAddPosterClick -> addPosterField()
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
        val updated = posters.toMutableList().apply {
            add(Poster())
        }
        setState { copy(posters = updated) }
    }
}