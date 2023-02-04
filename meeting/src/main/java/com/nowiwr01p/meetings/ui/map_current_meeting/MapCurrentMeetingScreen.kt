package com.nowiwr01p.meetings.ui.map_current_meeting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meetings.extensions.get
import com.nowiwr01p.meetings.ui.map_current_meeting.MapCurrentMeetingContract.*
import com.nowiwr01p.meetings.ui.map_current_meeting.data.MeetingStatus.*
import org.koin.androidx.compose.getViewModel

@Composable
fun MapCurrentMeeting(
    meeting: Meeting,
    navigator: Navigator,
    viewModel: MapCurrentMeetingViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init(meeting))
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
    if (state.meeting.cityName.isNotEmpty()) {
        Map(state)
    }
}

@Composable
private fun Toolbar(state: State, listener: Listener?) {
    ToolbarTop(
        title = {
            ToolbarTitle(title = state.title)
        },
        backIcon = {
            ToolbarBackButton {
                listener?.onBackClick()
            }
        }
    )
}

@Composable
private fun Map(state: State) {
    val startPoint = state.meeting.locationInfo.locationStartPoint.get()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPoint, 13f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    ) {
        Marker(
            state = rememberMarkerState(position = startPoint)
        )
        if (state.meetingStatus == IN_PROGRESS) {
            val coordinates = state.meeting.locationInfo.path.map { it.get() }
            Polyline(points = coordinates)
        }
    }
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