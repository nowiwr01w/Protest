package com.nowiwr01p.core_ui.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect> :
    ViewModel(), CoroutineScope {

    override val coroutineContext = SupervisorJob() + Dispatchers.Main

    abstract fun setInitialState(): UiState
    abstract fun handleEvents(event: Event)

    private val initialState: UiState by lazy {
        setInitialState()
    }

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    init {
        subscribeToEvents()
    }

    private val _viewState: MutableState<UiState> by lazy { mutableStateOf(initialState) }
    val viewState: State<UiState> by lazy { _viewState }

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    protected fun ViewModel.io(block: suspend () -> Unit): Job {
        return viewModelScope.launch(Dispatchers.Main) { block() }
    }
}