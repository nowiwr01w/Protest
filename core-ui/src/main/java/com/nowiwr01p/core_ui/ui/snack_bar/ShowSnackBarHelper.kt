package com.nowiwr01p.core_ui.ui.snack_bar

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ShowSnackBarHelper {

    private lateinit var scope: CoroutineScope
    private lateinit var scaffoldState: ScaffoldState

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
}