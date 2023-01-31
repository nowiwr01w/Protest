package com.nowiwr01p.meetings.ui.previews

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.previews.GetUnpublishedMeetingsUseCase
import com.nowiwr01p.meetings.ui.previews.MeetingsPreviewContract.*

class MeetingsPreviewViewModel(
    private val getUnpublishedMeetingsUseCase: GetUnpublishedMeetingsUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
        }
    }

    private fun init() = io {
        runCatching {
            getUnpublishedMeetingsUseCase.execute()
        }.onSuccess {
            setState { copy(meetings = it, loaded = true) }
        }
    }
}