package com.nowiwr01p.meetings.ui.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.ui.progress.StubProgressBar
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meetings.ui.main.MeetingItem
import com.nowiwr01p.meetings.ui.previews.MeetingsPreviewContract.*
import org.koin.androidx.compose.getViewModel

@Composable
fun MeetingsPreviewMainScreen(
    navigator: Navigator,
    viewModel: MeetingsPreviewViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    MeetingsPreviewMainScreenContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun MeetingsPreviewMainScreenContent(state: State, listener: Listener) = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
) {
    Toolbar(listener)
    if (state.loaded) {
        Meetings(state)
    } else {
        StubProgressBar()
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(listener: Listener) = ToolbarTop(
    title = {
        ToolbarTitle(title = "Готовы к публикации")
    },
    backIcon = {
        ToolbarBackButton {
            listener.onBackClick()
        }
    }
)

/**
 * MEETINGS
 */
@Composable
private fun Meetings(state: State) = LazyColumn(
    modifier = Modifier.fillMaxSize()
) {
    items(state.meetings) { meeting ->
        MeetingItem(meeting) {
            // TODO
        }
    }
}