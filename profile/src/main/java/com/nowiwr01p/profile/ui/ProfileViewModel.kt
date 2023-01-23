package com.nowiwr01p.profile.ui

import android.net.Uri
import androidx.core.net.toUri
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.profile.usecase.DeleteAccountUseCase
import com.nowiwr01p.domain.profile.usecase.LogOutUseCase
import com.nowiwr01p.domain.profile.usecase.UploadUserAvatarUseCase
import com.nowiwr01p.domain.user.usecase.UpdateUserNameUseCase
import com.nowiwr01p.profile.ui.ProfileContract.*

class ProfileViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val uploadUserAvatarUseCase: UploadUserAvatarUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val openLinksHelper: OpenLinksHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.editMode)
            is Event.OnSaveClick -> updateUserData()
            is Event.OnEditClick -> setEditMode(true)
            is Event.OnCancelClick -> setEditMode(false)
            is Event.SetStorageAvailable -> setStorageAvailable()
            is Event.OnUserNameChanged -> handleInput(event.name)
            is Event.RequestPermission -> requestPermission()
            is Event.ShowPermissionAlert -> showPermissionAlert(event.show)
            is Event.RedirectToSettings -> redirectToSettings()
            is Event.SetAvatarPreview -> setAvatarPreview(event.uri)
            is Event.RequestPermissionAlert -> requestPermissionAlert()
            is Event.Logout -> logout()
            is Event.DeleteAccount -> deleteAccount()
            is Event.OpenLink -> openLink(event.link)
        }
    }

    private fun init(editMode: Boolean) = io {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user, editMode = editMode) }
    }

    /**
     * CHANGE USER DATA
     */
    private fun updateUserData() = io {
        runCatching {
            updateUserName()
            updateUserAvatar()
        }.onSuccess {
            setState { copy(editMode = false, previewEditName = "", previewEditAvatar = "") }
        }
    }

    private suspend fun updateUserName() = with(viewState.value) {
        if (previewEditName.isNotBlank()) {
            updateUserNameUseCase.execute(previewEditName)
        }
    }

    private suspend fun updateUserAvatar() = with(viewState.value) {
        if (previewEditAvatar.isNotBlank()) {
            val updatedUser = uploadUserAvatarUseCase.execute(previewEditAvatar.toUri())
            setState { copy(user = updatedUser) }
        }
    }

    /**
     * EDIT MODE
     */
    private fun setEditMode(enabled: Boolean) {
        setState { copy(editMode = enabled) }
    }

    private fun setAvatarPreview(uri: Uri) {
        setState { copy(previewEditAvatar = uri.toString()) }
    }

    private fun handleInput(name: String) {
        if (name.length <= 24) setState { copy(previewEditName = name) }
    }

    /**
     * PREMISSIONS
     */
    private fun requestPermission() {
        setState { copy(shouldRequestPermission = true) }
    }

    private fun requestPermissionAlert() {
        setState { copy(shouldRequestAlert = true, shouldRequestPermission = true) }
    }

    private fun showPermissionAlert(show: Boolean) {
        setState { copy(showPermissionAlert = show, shouldRequestPermission = show) }
    }

    private fun redirectToSettings() {
        setEffect { Effect.RedirectToSettings }
        showPermissionAlert(false)
    }

    private fun setStorageAvailable() {
        setState { copy(isStorageAvailable = true, shouldRequestPermission = false) }
        setEffect { Effect.ChoosePhoto }
    }

    /**
     * EXIT
     */
    private fun logout() = io {
        runCatching {
            logOutUseCase.execute()
        }.onSuccess {
            setEffect { Effect.NavigateToAuth }
        }
    }

    private fun deleteAccount() = io {
        runCatching {
            deleteAccountUseCase.execute()
        }.onSuccess {
            setEffect { Effect.NavigateToAuth }
        }
    }

    /**
     * OPEN LINKS
     */
    private fun openLink(link: String) = io {
        openLinksHelper.openLink(link)
    }
}