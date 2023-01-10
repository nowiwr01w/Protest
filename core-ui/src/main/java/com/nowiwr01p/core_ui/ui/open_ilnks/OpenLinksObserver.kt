package com.nowiwr01p.core_ui.ui.open_ilnks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

private const val LAUNCH_LISTEN_FOR_LINKS = "listen_links_to_open"

@Composable
fun OpenLinkObserver(effect: Flow<String>, onEach: (String) -> Unit) {
    LaunchedEffect(LAUNCH_LISTEN_FOR_LINKS) {
        effect.onEach { onEach(it) }.collect()
    }
}