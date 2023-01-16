package com.nowiwr01p.core_ui.ui.animation

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.pressedAnimation(
    scale: Float = 0.9f,
    onClick: () -> Unit = {}
): Modifier = composed {

    val pressedState = remember {
        mutableStateOf(false)
    }
    val scaled = animateFloatAsState(
        targetValue = if (pressedState.value) scale else 1f
    )

    val scaleAnimation = scale(scaled.value)
    val motionAnimation = pointerInteropFilter {
        when (it.action) {
            MotionEvent.ACTION_DOWN -> {
                pressedState.value = true
            }
            MotionEvent.ACTION_UP -> {
                pressedState.value = false
                onClick.invoke()
            }
            MotionEvent.ACTION_CANCEL -> {
                pressedState.value = false
            }
        }
        true
    }
    then(scaleAnimation).then(motionAnimation)
}