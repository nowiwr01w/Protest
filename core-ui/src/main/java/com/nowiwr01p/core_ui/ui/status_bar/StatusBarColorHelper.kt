package com.nowiwr01p.core_ui.ui.status_bar

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatusBarColorHelper {

    private lateinit var scope: CoroutineScope

    private val _statusBarColor: MutableStateFlow<Color> = MutableStateFlow(Color.White)
    val statusBarColor: StateFlow<Color> = _statusBarColor

    fun init(coroutineScope: CoroutineScope) {
        scope = coroutineScope
    }

    fun setStatusBarColor(color: Color) = scope.launch {
        _statusBarColor.emit(color)
    }
}