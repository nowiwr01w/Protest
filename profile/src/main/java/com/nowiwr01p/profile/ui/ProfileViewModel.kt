package com.nowiwr01p.profile.ui

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
            is Event.OnEditClick -> setEditMode()
            is Event.OnSaveClick -> save()
            is Event.OnCancelClick -> cancel()
            is Event.OnChatClick -> toChat()
            is Event.OnUserNameChanged -> handleInput(event.name)
        }
    }

    private fun handleInput(name: String) {
        if (name.length <= 24) setState { copy(editNameValue = name) }
    }

    private fun init() = io {
        runCatching {
            getUser()
        }
    }

    private suspend fun getUser() {
        val user = getLocalUserUseCase.execute()
        setState { copy(user = user) }
    }

    private fun setEditMode() {
        setState { copy(editMode = true) }
    }

    private fun save() = io {
        runCatching {
            updateUserName()
        }.onSuccess {
            getUser()
            setState { copy(editMode = false) }
        }
    }

    private suspend fun updateUserName() {
        val updatedName = viewState.value.editNameValue.trim()
        updateUserNameUseCase.execute(updatedName)
    }

    private fun cancel() {
        setState { copy(editMode = false) }
    }

    private fun toChat() {

    }

}