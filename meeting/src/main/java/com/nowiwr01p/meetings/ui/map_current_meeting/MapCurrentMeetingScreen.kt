package com.nowiwr01p.meetings.ui.map_current_meeting

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.progress.StubProgressBar
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meetings.extensions.get
import com.nowiwr01p.meetings.ui.map_current_meeting.MapCurrentMeetingContract.*
import com.nowiwr01p.core.datastore.cities.data.MeetingStatus.*
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
        override fun runMeeting() {
            viewModel.setEvent(Event.RunMeeting)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init(meeting))
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.NavigateBack -> listener.onBackClick()
        }
    }

    MapCurrentMeetingContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun MapCurrentMeetingContent(state: State, listener: Listener?) = Box(
    modifier = Modifier.fillMaxSize()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(state, listener)
        Map(state)
    }
    ButtonsContainer(state, listener)
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(
    state: State,
    listener: Listener?
) {
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

/**
 * MAP
 */
@Composable
private fun Map(state: State) = if (state.meeting.locationInfo.locationStartPoint.latitude == .0) {
    StubProgressBar()
} else {
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
 * BOTTOM BUTTONS
 */
@Composable
private fun BoxScope.ButtonsContainer(state: State, listener: Listener?) = AnimatedVisibility(
    visible = state.user.organizer,
    exit = slideOutVertically(
        animationSpec = tween(delayMillis = 1000),
    ) + fadeOut(),
    enter = slideInVertically(
        initialOffsetY = { 250 },
        animationSpec = tween(delayMillis = 1000),
    ) + fadeIn(),
    modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
) {
    Buttons(state, listener)
}

@Composable
private fun Buttons(state: State, listener: Listener?) {
    val width = (LocalConfiguration.current.screenWidthDp.dp - 48.dp) / 2
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        AnimatedVisibility(visible = state.meetingStatus == IN_PROGRESS) {
            StateButton(
                text = "Изменить путь",
                modifier = Modifier
                    .widthIn(min = width)
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
            )
        }
        StateButton(
            text = if (state.meetingStatus == WAITING_FOR_PEOPLE) "Запустить" else "Отметить точку",
            state = state.runMeetingButtonState,
            onSendRequest = { listener?.runMeeting() },
            modifier = Modifier
                .widthIn(min = width)
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
        )
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