package com.nowiwr01p.profile.ui

import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface ProfileContract {

    sealed interface Event: ViewEvent {
        object Init: Event
        object OnEditClick: Event
        object OnSaveClick: Event
        object OnCancelClick: Event
        object OnChatClick: Event
    }

    data class State(
        val user: User = User(),
        val editMode: Boolean = false
    ): ViewState

    sealed interface Effect: ViewSideEffect {

    }

    interface Listener {
        fun onEditClick()
        fun onSaveClick()
        fun onCancelClick()
        fun onChatClick()
    }
}