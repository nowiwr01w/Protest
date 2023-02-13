package com.nowiwr01p.meetings.ui.meeting

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core_ui.ui.button.ButtonState.*
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.core_ui.ui.snack_bar.SnackBarParams
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.meetings.create_meeting.usecase.CreateMeetingUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.meeting.usecase.GetLocationUseCase
import com.nowiwr01p.domain.meetings.meeting.usecase.SetReactionUseCase
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode
import com.nowiwr01p.domain.meetings.meeting.data.CreateMeetingMode.SEND_TO_REVIEW
import com.nowiwr01p.domain.user.usecase.GetUserUseCase
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.*
import kotlinx.coroutines.delay

class MeetingViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val setReactionUseCase: SetReactionUseCase,
    private val createMeetingUseCase: CreateMeetingUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val openLinksHelper: OpenLinksHelper,
    private val showSnackBarHelper: ShowSnackBarHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.isPreviewMode, event.meeting)
            is Event.OpenLink -> openLink(event.link)
            is Event.SetReaction -> setReaction(event.isPositiveButtonClicked)
            is Event.CreateMeeting -> createMeeting(event.mode)
        }
    }

    private fun init(isPreviewMode: Boolean, meeting: Meeting) = io {
        setState { copy(meeting = meeting) }
        runCatching {
            getUserData()
            if (!isPreviewMode) getMeetingLocation()
        }.onSuccess {
            if (!isPreviewMode) delay(1000)
            setState { copy(loaded = true) }
        }
    }

    /**
     * LOAD LOCATION IF TODO
     */
    private suspend fun getMeetingLocation() = with(viewState.value) {
        runCatching {
            getLocationUseCase.execute(meeting.id)
        }.onSuccess {
            val updatedMeeting = meeting.copy(locationInfo = it)
            setState { copy(meeting = updatedMeeting) }
        }
    }

    /**
     * LOCAL USER DATA
     */
    private suspend fun getUserData() {
        val user = getUserUseCase.execute().value
        setState { copy(user = user) }
    }

    /**
     * WILL YOU GO REACTION
     */
    private fun setReaction(isPositiveButtonClicked: Boolean) = io {
        runCatching {
            val args = SetReactionUseCase.Args(viewState.value.meeting.id, isPositiveButtonClicked)
            setReactionUseCase.execute(args)
        }.onSuccess {
            setState { copy(meeting = it) }
        }
    }

    /**
     * CREATE MEETING FROM PREVIEW
     */
    private fun createMeeting(mode: CreateMeetingMode) = io {
        setState { copy(createMeetingButtonState = SEND_REQUEST) }
        runCatching {
            val args = CreateMeetingUseCase.Args(mode, viewState.value.meeting)
            createMeetingUseCase.execute(args)
        }.onSuccess {
            showSuccessSnackBar(mode)
            setState { copy(createMeetingButtonState = SUCCESS) }
        }.onFailure {
            setState { copy(createMeetingButtonState = ERROR) }
            delay(3000)
            setState { copy(createMeetingButtonState = DEFAULT) }
        }
    }

    private fun showSuccessSnackBar(mode: CreateMeetingMode) {
        val text = if (mode == SEND_TO_REVIEW) "Отправлено на ревью" else "Опубликовано"
        val params = SnackBarParams(
            text = text,
            endCallback = {
                setEffect { Effect.OnCreateMeetingSuccess }
            }
        )
        showSnackBarHelper.showSuccessSnackBar(params)
    }

    private fun openLink(link: String) = io {
        openLinksHelper.openLink(link)
    }
}