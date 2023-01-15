package com.nowiwr01p.core_ui.ui.snack_bar

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ShowSnackBarHelper {

    private lateinit var scope: CoroutineScope
    private lateinit var scaffoldState: ScaffoldState

    private val _text: Channel<String> = Channel()
    val text = _text.receiveAsFlow()

    fun init(coroutineScope: CoroutineScope, state: ScaffoldState) {
        scope = coroutineScope
        scaffoldState = state
    }

    fun showSnackBar(text: String) = scope.launch {
        scaffoldState.snackbarHostState.showSnackbar(
            message = text,
            duration = SnackbarDuration.Short
        )
    }

    fun showErrorSnackBar(text: String) = scope.launch {
        _text.send(text)
        delay(4000)
        _text.send("")
    }
}