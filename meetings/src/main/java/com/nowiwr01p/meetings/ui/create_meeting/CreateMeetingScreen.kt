package com.nowiwr01p.meetings.ui.create_meeting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.*
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateMeetingScreen(
    navigator: Navigator,
    viewModel: CreateMeetingVewModel = getViewModel()
) {
    val listener = object : Listener {

    }

    CreateMeetingScreenContent(listener)
}

@Composable
private fun CreateMeetingScreenContent(listener: Listener?) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar()
}

@Composable
private fun Toolbar() = ToolbarTop()