package com.nowiwr01p.map.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.subHeadlineRegular
import com.nowiwr01p.core_ui.theme.textColorSecondary
import com.nowiwr01p.core_ui.ui.progress.CenterScreenProgressBar
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.map.R
import com.nowiwr01p.map.ui.MapContract.*
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun MapMainScreen(
    navigator: Navigator,
    viewModel: MapViewModel = getViewModel()
) {
    val listener = object : Listener {

    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {

    }

    MapMainScreenContent(viewModel.viewState.value, listener)
}

@Composable
private fun MapMainScreenContent(
    state: State,
    listener: Listener?
) {
    if (state.showProgress) {
        CenterScreenProgressBar()
    } else {
        MapContent(state, listener)
    }
}

@Composable
fun MapContent(
    state: State,
    listener: Listener?
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(state, listener)
    Map(state, listener)
}

@Composable
private fun Toolbar(
    state: State,
    listener: Listener?
) = Surface(
    elevation = 4.dp,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_user),
            contentDescription = "Icon user",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
        )
        val userId = state.user.id.take(18)
        Text(
            text = "ID: $userId..",
            color = MaterialTheme.colors.textColorSecondary,
            style = MaterialTheme.typography.subHeadlineRegular,
            modifier = Modifier.padding(start = 16.dp)
        )
        IconButton(
            modifier = Modifier.padding(start = 4.dp),
            onClick = {  }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_copy),
                contentDescription = "Icon copy",
                modifier = Modifier.size(16.dp)
            )
        }
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
        Marker(
            state = rememberMarkerState(position = state.coordinates),
            onClick = {
                Timber.tag("Zhopa").d("click")
                true
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    MapContent(
        state = State(),
        listener = null
    )
}
