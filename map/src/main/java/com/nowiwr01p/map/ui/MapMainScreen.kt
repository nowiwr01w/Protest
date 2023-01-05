package com.nowiwr01p.map.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.ui.CenterScreenProgressBar
import com.nowiwr01p.core_ui.ui.EffectObserver
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
