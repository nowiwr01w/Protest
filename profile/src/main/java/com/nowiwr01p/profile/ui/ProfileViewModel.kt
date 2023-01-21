package com.nowiwr01p.profile.ui

import android.net.Uri
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.user.usecase.UpdateUserNameUseCase
import com.nowiwr01p.profile.ui.ProfileContract.*

class ProfileViewModel(
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.OnSaveClick -> save()
            is Event.OnChatClick -> toChat()
            is Event.OnEditClick -> setEditMode(true)
            is Event.OnCancelClick -> setEditMode(false)
            is Event.SetStorageAvailable -> setStorageAvailable()
            is Event.OnUserNameChanged -> handleInput(event.name)
            is Event.RequestPermission -> requestPermission()
            is Event.ShowPermissionAlert -> showPermissionAlert(event.show)
            is Event.RedirectToSettings -> redirectToSettings()
            is Event.SetAvatarPreview -> setAvatarPreview(event.uri)
        }
    }

    private fun init() = io {
        getUser()
    }

    private suspend fun getUser() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    private fun handleInput(name: String) {
        if (name.length <= 24) setState { copy(previewEditName = name) }
    }

    private fun save() = io {
        runCatching {
            updateUserName()
        }.onSuccess {
            getUser()
            setState { copy(editMode = false, previewEditName = "") }
        }
    }

    private suspend fun updateUserName() {
        val updatedName = viewState.value.previewEditName.trim()
        updateUserNameUseCase.execute(updatedName)
    }

    private fun setEditMode(enabled: Boolean) {
        setState { copy(editMode = enabled) }
    }

    private fun setAvatarPreview(uri: Uri) {
        setState { copy(previewEditAvatar = uri.toString()) }
    }

    private fun requestPermission() {
        setState { copy(shouldRequestPermission = true) }
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

    private fun toChat() {

    }
}