package com.nowiwr01p.meetings.ui.create_meeting.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.nowiwr01p.core.model.CreateMeetingMapType
import com.nowiwr01p.core.model.CreateMeetingMapType.*
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.NavKeys.NAV_ARG_PATH
import com.nowiwr01p.core_ui.NavKeys.NAV_ARG_START_LOCATION
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.mainBackgroundColor
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meeting.R
import com.nowiwr01p.meetings.ui.create_meeting.map.CreateMeetingMapContract.*
import com.nowiwr01p.meetings.ui.create_meeting.map.CreateMeetingMapContract.State
import org.koin.androidx.compose.getViewModel

@Composable
fun CurrentMeetingMapScreen(
    type: CreateMeetingMapType,
    navigator: Navigator,
    viewModel: CreateMeetingMapViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
        override fun onSavePathClick() {
            viewModel.setEvent(Event.OnSavePathClick)
        }
        override fun selectCoordinates(position: LatLng) {
            viewModel.setEvent(Event.SelectCoordinates(position))
        }
        override fun removeLastCoordinate() {
            viewModel.setEvent(Event.RemoveLastCoordinate)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.OnSavePathClick -> {
                val arg = if (type == DRAW_PATH) NAV_ARG_PATH else NAV_ARG_START_LOCATION
                navigator.setScreenResult(arg, it.path)
                navigator.navigateUp()
            }
        }
    }

    CurrentMeetingScreenContent(
        type = type,
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun CurrentMeetingScreenContent(
    type: CreateMeetingMapType,
    state: State,
    listener: Listener?
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(type, listener)
        if (state.cityCoordinates.latitude != .0) {
            Map(type, state, listener)
        }
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(
    type: CreateMeetingMapType,
    listener: Listener?
) {
    ToolbarTop(
        title = {
            val title = if (type == DRAW_PATH) "Нарисуйте путь" else "Выберите место встречи"
            ToolbarTitle(title = title)
        },
        backIcon = {
            ToolbarBackButton { listener?.onBackClick() }
        },
        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { listener?.onSavePathClick() }
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Check),
                    contentDescription = "Toolbar back icon",
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    )
}

/**
 * MAP
 */
@Composable
private fun Map(
    type: CreateMeetingMapType,
    state: State,
    listener: Listener?
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.cityCoordinates, 13f)
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (map, container) = createRefs()

        val mapsModifier = Modifier
            .constrainAs(map) {
                height = Dimension.fillToConstraints
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        GoogleMap(
            modifier = mapsModifier,
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            Polyline(points = state.selectedCoordinates)
        }

        Box(
            modifier = mapsModifier.padding(bottom = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_location_marker),
                contentDescription = "Location Marker",
                tint = Color(0xFFFC4C4C)
            )
        }

        val bottomButtonsModifier = Modifier
            .fillMaxWidth()
            .constrainAs(container) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, 32.dp)
            }
        BottomButtons(
            type = type,
            state = state,
            listener = listener,
            position = cameraPositionState.position.target,
            modifier = bottomButtonsModifier
        )
    }
}

/**
 * UNDO & CHOOSE BUTTONS
 */
@Composable
private fun BottomButtons(
    type: CreateMeetingMapType,
    state: State,
    listener: Listener?,
    position: LatLng,
    modifier: Modifier
) {
    Row(modifier = modifier) {
        AnimatedVisibility(visible = state.selectedCoordinates.isNotEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 32.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.mainBackgroundColor)
                    .clickable {
                        listener?.removeLastCoordinate()
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_undo),
                    contentDescription = "Undo button",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        StateButton(
            text = "Выбрать",
            enabled = if (type == SELECT_START_LOCATION) state.selectedCoordinates.isEmpty() else true,
            onSendRequest = { listener?.selectCoordinates(position) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
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
    Map(
        type = DRAW_PATH,
        state = State(),
        listener = null
    )
}