package com.nowiwr01p.meetings.ui.meeting

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.*

class MeetingViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val openLinksHelper: OpenLinksHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meeting)
            is Event.OpenLink -> openLink(event.link)
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
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    private fun openLink(link: String) = io {
        openLinksHelper.openLink(link)
    }
}