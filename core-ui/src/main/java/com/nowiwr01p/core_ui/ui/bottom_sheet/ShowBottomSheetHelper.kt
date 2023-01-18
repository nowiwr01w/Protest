package com.nowiwr01p.core_ui.ui.bottom_sheet

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

typealias Input = BottomSheetParams?

@OptIn(ExperimentalMaterialApi::class)
class ShowBottomSheetHelper {

    private lateinit var scope: CoroutineScope
    private lateinit var bottomSheetState: ModalBottomSheetState

    private val _content: Channel<Input> = Channel()
    val content = _content.receiveAsFlow()

    fun init(coroutineScope: CoroutineScope, modalBottomSheetState: ModalBottomSheetState) {
        scope = coroutineScope
        bottomSheetState = modalBottomSheetState
    }

    fun showBottomSheet(content: Input) = scope.launch {
        _content.send(content)
        bottomSheetState.show()
    }

    fun closeBottomSheet(delay: Long = 0L) = scope.launch {
        delay(delay)
        _content.send(null)
        bottomSheetState.hide()
    }

    fun getBottomSheetState() = bottomSheetState
}