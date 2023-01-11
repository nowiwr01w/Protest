package com.nowiwr01p.meetings.ui.map_all_meetings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.setStatusBarTransparent
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.ui.progress.CenterScreenProgressBar
import com.nowiwr01p.meetings.ui.map_all_meetings.MapContract.*
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun MapAllMeetingsScreen(
    navigator: Navigator,
    viewModel: MapViewModel = getViewModel()
) {
    val state = viewModel.viewState.value
    LocalContext.current.setStatusBarTransparent(state.transparentStatusBar)

    val listener = object : Listener {
        override fun onBack() {
            viewModel.setEvent(Event.OnBackClick)
        }
        override fun onMeetingClick(meeting: Meeting) {
            Timber.tag("Zhopa").d("meeting id = ${meeting.id}")
        }
    }

    BackHandler {
        listener.onBack()
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.OnBackClick -> {
                navigator.navigateUp()
            }
        }
    }

    MapMainScreenContent(state, listener)
}

@Composable
private fun MapMainScreenContent(
    state: State,
    listener: Listener?
) {
    if (state.showProgress) {
        CenterScreenProgressBar()
    } else {
        Map(state, listener)
    }
}

@Composable
private fun Map(
    state: State,
    listener: Listener?
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.coordinates, 11f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        state.meetings.forEach { meeting ->
            Marker(meeting) {
                listener?.onBack()
            }
        }
    }
}

@Composable
private fun Marker(meeting: Meeting, onClick: () -> Unit) = with(meeting) {
    val position = LatLng(
        locationInfo.coordinates.latitude,
        locationInfo.coordinates.longitude
    )
    Marker(
        state = rememberMarkerState(position = position),
        onClick = {
            onClick.invoke()
            true
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    Map(
        state = State(),
        listener = null
    )
}
