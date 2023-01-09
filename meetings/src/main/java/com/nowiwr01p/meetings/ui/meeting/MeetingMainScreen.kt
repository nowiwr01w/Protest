package com.nowiwr01p.meetings.ui.meeting

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.shadowCard
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meetings.R
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.*
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.State
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun MeetingMainScreen(
    navigator: Navigator,
    viewModel: MeetingViewModel = getViewModel()
) {
   val listener = object : Listener {

   }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {

    }

    MeetingMainScreenContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun MeetingMainScreenContent(
    state: State,
    listener: Listener?
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    val meeting = state.meeting
    Toolbar()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { TopImage(meeting) }
        item { Categories(meeting) }
        item { Title(meeting) }
        item { Description(meeting) }
        item { LocationTitle() }
        item { LocationInfoContainer(meeting) }
        item { MapPreview(meeting) }
        item { MapPlaceComment(meeting) }
        item { TakeWithYouTitle() }
        item { TakeWithYouDetails(meeting) }
        item { TakeWithYouList(meeting) }
        item { DropdownItems(meeting) }
        item { WillYouGoTitle() }
        item { WillYouGoPeopleCount(meeting) }
        item { WillYouGoImage() }
        item { WillYouGoActionButtons(meeting) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar() = ToolbarTop(
    backIcon = {
        ToolbarBackButton {

        }
    },
    actions = {
        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(14.dp))
                .clickable {  }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_telegram),
                contentDescription = "Telegram chat icon",
                modifier = Modifier.padding(6.dp)
            )
        }
    }
)

/**
 * IMAGE
 */
@Composable
private fun TopImage(meeting: Meeting) = CoilImage(
    imageModel = { meeting.image },
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clip(RoundedCornerShape(16.dp))
)

/**
 * CATEGORY
 */
@Composable
private fun Categories(meeting: Meeting) = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
) {
    items(meeting.categories) { category ->
        Category(
            text = category,
            textColor = MaterialTheme.colors.graphicsBlue,
            backgroundColor = MaterialTheme.colors.backgroundBlue
        )
    }
    item {
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun Category(
    text: String,
    textColor: Color,
    backgroundColor: Color
) = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
        .padding(start = 16.dp)
        .clip(RoundedCornerShape(40))
        .background(backgroundColor)
) {
    Text(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        text = text,
        color = textColor,
        style = MaterialTheme.typography.caption2Regular,
    )
}

/**
 * TITLE
 */
@Composable
private fun Title(meeting: Meeting) = Text(
    text = meeting.title,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.title2Bold,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

/**
 * DESCRIPTION
 */
@Composable
private fun Description(meeting: Meeting) = Text(
    text = meeting.description,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

/**
 * LOCATION: TITLE, PLACE, MAP & DATE
 */
@Composable
private fun LocationTitle() = Text(
    text = "Где встречаемся",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.title2Bold,
    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun LocationInfoContainer(meeting: Meeting) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
) {
    LocationPlace(meeting.locationInfo.shortName)
    Spacer(modifier = Modifier.weight(1f))
    LocationDate(meeting.date.toString())
}

@Composable
private fun LocationPlace(shortName: String) = Text(
    text = shortName,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1
)

@Composable
private fun LocationDate(placeDetails: String) = Text(
    text = placeDetails,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1
)

@Composable
private fun MapPreview(meeting: Meeting) {
    val coordinates = with(meeting.locationInfo.coordinates) {
        LatLng(latitude, longitude)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coordinates, 13f)
    }
    GoogleMap(
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Marker(
            state = rememberMarkerState(position = coordinates),
            onClick = {
                Timber.tag("Zhopa").d("click")
                true
            }
        )
    }
}

@Composable
private fun MapPlaceComment(meeting: Meeting) = Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxSize()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp),
) {
    Text(
        text = meeting.locationInfo.placeDetails,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.footnoteRegular,
        color = MaterialTheme.colors.textColorSecondary,
    )
}

/**
 * WHAT TO TAKE WITH YOU
 */
@Composable
private fun TakeWithYouTitle() = Text(
    text = "Что брать с собой",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.title2Bold,
    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun TakeWithYouDetails(meeting: Meeting) = Text(
    text = meeting.takeWithYouInfo.description,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun TakeWithYouList(meeting: Meeting) = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
) {
    items(meeting.takeWithYouInfo.posterLinks) {
        ThingItem()
    }
    item { Spacer(modifier = Modifier.width(16.dp)) }
}

@Composable
private fun ThingItem() = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(start = 16.dp)
) {
    CoilImage(
        imageModel = { R.drawable.ic_poster },
        modifier = Modifier
            .size(96.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {}
    )
    Text(
        text = "Плакат",
        style = MaterialTheme.typography.caption2Regular,
        color = MaterialTheme.colors.textPrimary,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
private fun DropdownItems(meeting: Meeting) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        .shadowCard(
            elevation = 6.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.backgroundSpecial
        )
) {
    DropdownItem(
        title = "Чего хотим добиться",
        steps = meeting.details.goals
    )
    DropdownItem(
        title = "Лозунги",
        steps = meeting.details.slogans
    )
    DropdownItem(
        title = "Стратегия",
        steps = meeting.details.strategy,
        lastItem = true
    )
}

@Composable
private fun DropdownItem(
    title: String,
    steps: List<String>,
    lastItem: Boolean = false
) {
    val expanded = remember { mutableStateOf(false) }
    val rotateAnimation by animateFloatAsState(
        targetValue = if (expanded.value) 180f else 0f,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                expanded.value = !expanded.value
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.textPrimary,
                style = MaterialTheme.typography.bodyRegular,
                modifier = Modifier.padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.KeyboardArrowDown),
                    contentDescription = "Drop down icon",
                    tint = MaterialTheme.colors.graphicsTertiary,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .rotate(rotateAnimation)
                        .clickable { expanded.value = !expanded.value }

                )
            }
        }
        if (expanded.value) {
            steps.forEach { step ->
                StepItem(step)
            }
            Spacer(modifier = Modifier.height(if (lastItem) 16.dp else 8.dp))
        }
    }
}

@Composable
private fun StepItem(text: String) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 24.dp, end = 24.dp, bottom = 6.dp)
) {
    Text(
        text = "–",
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.textPrimary
    )
    Spacer(modifier = Modifier.width(12.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.textPrimary
    )
}

/**
 * WILL YOU GO
 */
@Composable
private fun WillYouGoTitle() = Text(
    text = "Увидимся на митинге?",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.title2Bold,
    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun WillYouGoPeopleCount(meeting: Meeting) = Text(
    text = "${meeting.reaction.peopleGoCount} человек точно пойдут\nЕщё ${meeting.reaction.peopleMaybeGoCount}, возможно, тоже\nА ты?",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun WillYouGoImage() = Row(
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 24.dp)
) {
    val width = LocalConfiguration.current.screenWidthDp * 3/5
    Image(
        painter = painterResource(R.drawable.image_call_to_go),
        contentDescription = "Will you go image",
        modifier = Modifier.width(width.dp)
    )
}

@Composable
private fun WillYouGoActionButtons(meeting: Meeting) = Row(
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 24.dp)
) {
    WillYouGoActionButton(
        text = "Пойду",
        borderColor = MaterialTheme.colors.graphicsGreen.copy(alpha = 0.1f),
        textColor = MaterialTheme.colors.textPositive
    )
    Spacer(
        modifier = Modifier.width(24.dp)
    )
    WillYouGoActionButton(
        text = "Мб пойду",
        borderColor = Color(0xFFD3D3D3).copy(alpha = 0.25f),
        textColor = Color(0xFFD3D3D3)
    )
}

@Composable
private fun WillYouGoActionButton(
    text: String,
    borderColor: Color,
    textColor: Color
) {
    val width = LocalConfiguration.current.screenWidthDp * 1/3
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(width.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headline,
            color = textColor,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

/**
 * PREVIEWS
 */
@Preview(showBackground = true)
@Composable
private fun MeetingMainScreenContent() = MeetingsTheme {
    MeetingMainScreenContent(
        state = State(),
        listener = null
    )
}