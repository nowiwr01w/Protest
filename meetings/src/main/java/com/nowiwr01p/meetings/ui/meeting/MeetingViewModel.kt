package com.nowiwr01p.meetings.ui.meeting

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.meetings.usecase.SetReactionUseCase
import com.nowiwr01p.domain.meetings.usecase.SetReactionUseCase.Args
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.*

class MeetingViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val setReactionUseCase: SetReactionUseCase,
    private val openLinksHelper: OpenLinksHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meeting)
            is Event.OpenLink -> openLink(event.link)
            is Event.SetReaction -> setReaction(event.isPositiveButtonClicked)
        }
    }

    private fun init(meeting: Meeting) = io {
        runCatching {
            getUserData()
        }.onSuccess {
            setState { copy(meeting = meeting) }
        }
    }

    private suspend fun getUserData() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    private fun setReaction(isPositiveButtonClicked: Boolean) = io {
        runCatching {
            val args = Args(viewState.value.meeting.id, isPositiveButtonClicked)
            setReactionUseCase.execute(args)
        }.onSuccess {
            setState { copy(meeting = it) }
        }
    }

    private fun openLink(link: String) = io {
        openLinksHelper.openLink(link)
    }
}