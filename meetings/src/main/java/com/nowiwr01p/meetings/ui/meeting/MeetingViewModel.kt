package com.nowiwr01p.meetings.ui.meeting

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.location.usecase.local.GetLocalCityUseCase
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.*

class MeetingViewModel(
    private val getLocalCityUseCase: GetLocalCityUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meeting)
        }
    }

    private fun init(meeting: Meeting) = io {
        runCatching {
            getUserCity()
        }.onSuccess {
            setState { copy(meeting = meeting) }
        }
    }

    private suspend fun getUserCity() {
        val city = getLocalCityUseCase.execute()
        setState { copy(city = city) }
    }
}