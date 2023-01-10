package com.nowiwr01p.meetings.ui.meeting

import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.ReactionLoadingStatus.*

interface MeetingContract {

    sealed interface Event: ViewEvent {
        data class Init(val meeting: Meeting): Event
        data class OpenLink(val link: String): Event
    }

    data class State(
        val user: User = User(),
        val meeting: Meeting = Meeting.getSampleData(),
        val positiveReaction: ReactionLoadingStatus = DEFAULT,
        val negativeReaction: ReactionLoadingStatus = DEFAULT,
    ): ViewState

    enum class ReactionLoadingStatus {
        DEFAULT, SEND_REQUEST, SUCCESS, ERROR
    }

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {
        fun onBack()
        fun openLink(link: String)
    }
}