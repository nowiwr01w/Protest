package com.nowiwr01p.profile.ui

import android.net.Uri
import androidx.core.net.toUri
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.core_ui.ui.snack_bar.SnackBarParams
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.app.InitAppDataUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.profile.usecase.DeleteAccountUseCase
import com.nowiwr01p.domain.profile.usecase.LogOutUseCase
import com.nowiwr01p.domain.user.usecase.GetUserUseCase
import com.nowiwr01p.domain.user.usecase.UpdateUserAvatarUseCase
import com.nowiwr01p.domain.user.usecase.UpdateUserNameUseCase
import com.nowiwr01p.profile.ui.ProfileContract.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val updateUserAvatarUseCase: UpdateUserAvatarUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val initAppDataUseCase: InitAppDataUseCase,
    private val openLinksHelper: OpenLinksHelper,
    private val showSnackBarHelper: ShowSnackBarHelper
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
            is Event.ShowLogoutAlert -> showLogoutAlert(event.show)
            is Event.ShowDeleteAccountAlert -> showDeleteAccountAlert(event.show)
        }
    }

    private fun init(editMode: Boolean) {
        setState { copy(editMode = editMode) }
        getUserData()
    }

    /**
     * GET USER DATA
     */
    private fun getUserData() = launch {
        getUserUseCase.execute().collect { user ->
            setState { copy(user = user) }
        }
    }

    /**
     * CHANGE USER DATA
     */
    private fun updateUserData() = io {
        setState { copy(editProgress = true) }
        runCatching {
            updateUserName()
            updateUserAvatar()
        }.onSuccess {
            onUserDataUpdatedSuccess()
        }.onFailure {
            onUserDataUpdatedFailed()
        }
    }

    private suspend fun updateUserName() = with(viewState.value) {
        if (previewEditName.isNotBlank()) {
            updateUserNameUseCase.execute(previewEditName)
        }
    }

    private suspend fun updateUserAvatar() = with(viewState.value) {
        if (previewEditAvatar.isNotBlank()) {
            updateUserAvatarUseCase.execute(previewEditAvatar.toUri())
        }
    }

    private fun onUserDataUpdatedSuccess() = setState {
        copy(
            editMode = false,
            editProgress = false,
            previewEditName = "",
            previewEditAvatar = ""
        )
    }

    private fun onUserDataUpdatedFailed() {
        val params = SnackBarParams("Не удалось сохранить изменения. Попробуйте позже.")
        showSnackBarHelper.showErrorSnackBar(params)
        setState { copy(editMode = false, editProgress = false) }
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
            initAppDataUseCase.clearSubscribed()
        }.onSuccess {
            setEffect { Effect.NavigateToAuth }
        }
    }

    private fun deleteAccount() = io {
        runCatching {
            deleteAccountUseCase.execute()
            initAppDataUseCase.clearSubscribed()
        }.onSuccess {
            setEffect { Effect.NavigateToAuth }
        }
    }

    private fun showLogoutAlert(show: Boolean) {
        setState { copy(showLogoutAlert = show) }
    }

    private fun showDeleteAccountAlert(show: Boolean) {
        setState { copy(showDeleteAccountAlert = show) }
    }

    /**
     * OPEN LINKS
     */
    private fun openLink(link: String) = io {
        openLinksHelper.openLink(link)
    }
}