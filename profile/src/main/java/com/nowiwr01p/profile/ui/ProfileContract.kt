package com.nowiwr01p.profile.ui

import android.net.Uri
import com.nowiwr01p.core.model.User
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinkListener
import com.nowiwr01p.core_ui.view_model.ViewEvent
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import com.nowiwr01p.core_ui.view_model.ViewState

interface ProfileContract {

    sealed interface Event: ViewEvent {
        data class Init(val editMode: Boolean): Event
        object OnEditClick: Event
        object OnSaveClick: Event
        object OnCancelClick: Event
        object RequestPermission: Event
        object RequestPermissionAlert: Event
        object RedirectToSettings: Event
        object SetStorageAvailable: Event
        object Logout: Event
        object DeleteAccount: Event
        data class OpenLink(val link: String): Event
        data class SetAvatarPreview(val uri: Uri): Event
        data class OnUserNameChanged(val name: String): Event
        data class ShowPermissionAlert(val show: Boolean): Event
        data class ShowLogoutAlert(val show: Boolean): Event
        data class ShowDeleteAccountAlert(val show: Boolean): Event
    }

    data class State(
        val user: User = User(),
        val previewEditName: String = "",
        val previewEditAvatar: String = "",
        val editMode: Boolean = false,
        val editProgress: Boolean = false,
        val showPermissionAlert: Boolean = false,
        val shouldRequestAlert: Boolean = false,
        val shouldRequestPermission: Boolean = false,
        val isStorageAvailable: Boolean = false,
        val showLogoutAlert: Boolean = false,
        val showDeleteAccountAlert: Boolean = false
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        object ChoosePhoto: Effect
        object RedirectToSettings: Effect
        object NavigateToAuth: Effect
    }

    interface Listener: OpenLinkListener {
        fun onEditClick()
        fun onSaveClick()
        fun onCancelClick()
        fun onUserNameChanged(name: String)
        fun requestPermission()
        fun requestPermissionAlert()
        fun showPermissionAlert(show: Boolean)
        fun redirectToSettings()
        fun setStorageAvailable()
        fun setAvatarPreview(uri: Uri)
        fun toChangeCity()
        fun logout()
        fun copyUserId()
        fun deleteAccount()
        fun showLogoutAlert(show: Boolean)
        fun showDeleteAccountAlert(show: Boolean)
    }
}