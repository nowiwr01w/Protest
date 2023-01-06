package com.nowiwr01p.core_ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.nowiwr01p.core_ui.view_model.ViewSideEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

private const val LAUNCH_LISTEN_FOR_EFFECTS = "launch-listen-to-effects"

@Composable
fun <T : ViewSideEffect> EffectObserver(effect: Flow<T>, onEach: (T) -> Unit) {
    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effect.onEach {
            onEach(it)
        }.collect()
    }
}