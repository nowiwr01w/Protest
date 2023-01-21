package com.nowiwr01p.profile.ui

import android.net.Uri
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
        object RequestPermission: Event
        object SetStorageAvailable: Event
        data class SetAvatarPreview(val uri: Uri): Event
        data class OnUserNameChanged(val name: String): Event
    }

    data class State(
        val user: User = User(),
        val previewEditName: String = "",
        val previewEditAvatar: String = "",
        val editMode: Boolean = false,
        val shouldRequestPermission: Boolean = false,
        val isStorageAvailable: Boolean = false
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object ChoosePhoto: Effect
    }

    interface Listener {
        fun onEditClick()
        fun onSaveClick()
        fun onCancelClick()
        fun onChatClick()
        fun onUserNameChanged(name: String)
        fun requestPermission()
        fun setStorageAvailable()
        fun setAvatarPreview(uri: Uri)
    }
}