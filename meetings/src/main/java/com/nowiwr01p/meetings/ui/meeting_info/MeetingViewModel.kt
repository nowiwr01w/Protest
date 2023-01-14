package com.nowiwr01p.meetings.ui.meeting_info

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.ui.button.ButtonState
import com.nowiwr01p.core_ui.ui.button.ButtonState.*
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.cteate_meeting.CreateMeetingUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.meeting_info.SetReactionUseCase
import com.nowiwr01p.domain.meeting_info.SetReactionUseCase.Args
import com.nowiwr01p.meetings.ui.meeting_info.MeetingContract.*
import kotlinx.coroutines.delay

class MeetingViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val setReactionUseCase: SetReactionUseCase,
    private val createMeetingUseCase: CreateMeetingUseCase,
    private val openLinksHelper: OpenLinksHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meeting)
            is Event.OpenLink -> openLink(event.link)
            is Event.SetReaction -> setReaction(event.isPositiveButtonClicked)
            is Event.CreateMeeting -> createMeeting()
        }
    }

    private fun init(meeting: Meeting) = io {
        setState { copy(meeting = meeting, loaded = true) }
        getUserData()
    }

    /**
     * LOCAL USER DATA
     */
    private suspend fun getUserData() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    /**
     * WILL YOU GO REACTION
     */
    private fun setReaction(isPositiveButtonClicked: Boolean) = io {
        runCatching {
            val args = Args(viewState.value.meeting.id, isPositiveButtonClicked)
            setReactionUseCase.execute(args)
        }.onSuccess {
            setState { copy(meeting = it) }
        }
    }

    /**
     * CREATE MEETING FROM PREVIEW
     */
    private fun createMeeting() = io {
        setState { copy(createMeetingButtonState = SEND_REQUEST) }
        runCatching {
            createMeetingUseCase.execute(viewState.value.meeting)
        }.onSuccess {
            setState { copy(createMeetingButtonState = SUCCESS) }
        }.onFailure {
            setState { copy(createMeetingButtonState = ERROR) }
            delay(3000)
            setState { copy(createMeetingButtonState = DEFAULT) }
        }
    }

    private fun openLink(link: String) = io {
        openLinksHelper.openLink(link)
    }
}