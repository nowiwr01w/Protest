package com.nowiwr01p.meetings.ui.map_current_meeting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meetings.ui.map_current_meeting.MapCurrentMeetingContract.*
import org.koin.androidx.compose.getViewModel

@Composable
fun MapCurrentMeeting(
    meetingId: String,
    navigator: Navigator,
    viewModel: MapCurrentMeetingViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init(meetingId))
    }

    MapCurrentMeetingContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun MapCurrentMeetingContent(state: State, listener: Listener?) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(state, listener)
    Map()
}

@Composable
private fun Toolbar(state: State, listener: Listener?) = ToolbarTop(
    title = {
        ToolbarTitle(title = "Текущий митинг")
    },
    backIcon = {
        ToolbarBackButton {
            listener?.onBackClick()
        }
    }
)

@Composable
private fun Map() {

}

/**
 * PREVIEW
 */
@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    MapCurrentMeetingContent(
        state = State(),
        listener = null
    )
}