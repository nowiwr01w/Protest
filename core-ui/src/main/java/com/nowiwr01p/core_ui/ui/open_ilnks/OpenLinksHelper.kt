package com.nowiwr01p.core_ui.ui.open_ilnks

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class OpenLinksHelper {

    private val _openLink: Channel<String> = Channel()
    val openLink = _openLink.receiveAsFlow()

    suspend fun openLink(link: String) {
        _openLink.send(link)
    }
}