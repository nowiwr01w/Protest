package com.nowiwr01p.meetings.ui.meeting

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.extenstion.formatToDateTime
import com.nowiwr01p.core.extenstion.getPeopleGoCount
import com.nowiwr01p.core.extenstion.getPeopleMaybeGoCount
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.R.raw
import com.nowiwr01p.core_ui.extensions.shadowCard
import com.nowiwr01p.core_ui.extensions.toColor
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meeting.R
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.*
import com.nowiwr01p.meetings.ui.meeting.MeetingContract.State
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun MeetingMainScreen(
    isPreviewMode: Boolean,
    meeting: Meeting,
    navigator: Navigator,
    viewModel: MeetingViewModel = getViewModel()
) {
   val listener = object : Listener {
       override fun onBack() {
           navigator.navigateUp()
       }
       override fun openLink(link: String) {
           viewModel.setEvent(Event.OpenLink(link))
       }
       override fun setReaction(isPositiveButtonClicked: Boolean) {
            viewModel.setEvent(Event.SetReaction(isPositiveButtonClicked))
       }
       override fun createMeeting() {
            viewModel.setEvent(Event.CreateMeeting)
       }
   }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init(meeting))
    }

    MeetingMainScreenContent(
        isPreviewMode = isPreviewMode,
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun MeetingMainScreenContent(
    isPreviewMode: Boolean,
    state: State,
    listener: Listener?
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (toolbar, content, publishButton) = createRefs()

        val toolbarModifier = Modifier
            .constrainAs(toolbar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        Toolbar(
            state = state,
            listener = listener,
            modifier = toolbarModifier
        )

        val contentModifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .constrainAs(content) {
                height = Dimension.fillToConstraints
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(toolbar.bottom)
                bottom.linkTo(parent.bottom)
            }
        ScrollableContent(
            isPreviewMode = isPreviewMode,
            state = state,
            listener = listener,
            modifier = contentModifier
        )

        val publishButtonModifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
            .clip(RoundedCornerShape(24.dp))
            .constrainAs(publishButton) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        if (isPreviewMode) {
            PublishButton(
                state = state,
                listener = listener,
                modifier = publishButtonModifier
            )
        }
    }
}

@Composable
private fun ScrollableContent(
    isPreviewMode: Boolean,
    state: State,
    listener: Listener?,
    modifier: Modifier
) {
    val meeting = state.meeting
    LazyColumn(modifier) {
        item { TopImage(meeting) }
        item { Categories(meeting) }
        item { Title(meeting) }
        item { Description(meeting) }
        item { LocationTitle() }

        if (state.meeting.cityName == "everywhere") {
            item { MeetingEveryWhereLocation() }
        } else {
            item { LocationInfoContainer(state) }
            if (state.loaded) {
                item { MapPreview(state) }
            }
            item { MapPlaceComment(meeting) }
        }

        val posters = state.meeting.takeWithYouInfo.posters.isNotEmpty()
        val motivation = state.meeting.takeWithYouInfo.postersMotivation.isNotEmpty()

        if (posters || motivation) {
            item { TakeWithYouTitle() }
        }
        if (motivation) {
            item { TakeWithYouDetails(meeting) }
        }
        if (posters) {
            item { Posters(meeting, listener) }
        }

        with(state.meeting.details) {
            if (goals.isNotEmpty() || slogans.isNotEmpty() || strategy.isNotEmpty()) {
                item { DropdownItems(meeting) }
            }
        }

        if (isPreviewMode) {
            item { Spacer(modifier = Modifier.height(120.dp)) }
        } else {
            item { WillYouGoTitle() }
            item { WillYouGoPeopleCount(meeting) }
            item { WillYouGoImage() }
            item { WillYouGoActionButtons(state, listener) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(
    state: State,
    listener: Listener?,
    modifier: Modifier
) {
    ToolbarTop(
        modifier = modifier,
        backIcon = {
            ToolbarBackButton {
                listener?.onBack()
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                        listener?.openLink(state.meeting.telegram)
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_telegram),
                    contentDescription = "Telegram chat icon",
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    )
}

/**
 * IMAGE
 */
@Composable
private fun TopImage(meeting: Meeting) = CoilImage(
    imageModel = { meeting.image },
    modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
)

/**
 * CATEGORY
 */
@Composable
private fun Categories(meeting: Meeting) = FlowRow(
    mainAxisSize = SizeMode.Wrap,
    mainAxisSpacing = 8.dp,
    crossAxisSpacing = 8.dp,
    mainAxisAlignment = FlowMainAxisAlignment.Start,
    lastLineMainAxisAlignment = FlowMainAxisAlignment.Start,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, start = 16.dp)
) {
    meeting.categories.sortedBy { it.priority }.forEach {
        Category(it)
    }
}

@Composable
private fun Category(
    category: Category
) = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
        .clip(RoundedCornerShape(40))
        .background(category.backgroundColor.toColor())
) {
    Text(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        text = category.name,
        color = category.textColor.toColor(),
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
private fun LocationInfoContainer(state: State) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
) {
    with(state.meeting) {
        if (requiredPeopleCount != 0) {
            val text = "Митинг будет на следующий день после того, как наберётся " +
                    "$requiredPeopleCount человек.\n" +
                    "Это обязательное условие."
            LocationPlace(text)
        } else {
            LocationPlace(locationInfo.locationName)
            Spacer(modifier = Modifier.weight(1f))
            LocationDate(date.formatToDateTime())
        }
    }
}

@Composable
private fun LocationPlace(shortName: String) = Text(
    text = shortName,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1
)

@Composable
private fun LocationDate(date: String) = Text(
    text = date,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1
)

@Composable
private fun MeetingEveryWhereLocation() = Text(
    text = "Идеальный сценарий - выйти на центральные площади городов.\n" +
            "Но жизнь не идеальна, поэтому время и место встречи можно посмотреть у нас в Telegram канале.\n\n" +
            "Если вы организатор, а вашего города нет в списке - создайте его и напишите нам в Telegram - @nowiwr01m",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun MapPreview(state: State) {
    val coordinates = with(state) {
        if (meeting.requiredPeopleCount != 0) cityCoordinates else meetingCoordinates
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coordinates, 11f)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(LocalContext.current, raw.map_settings)
            ),
            uiSettings = MapUiSettings(
                compassEnabled = false,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = false,
                rotationGesturesEnabled = false,
                scrollGesturesEnabled = false,
                scrollGesturesEnabledDuringRotateOrZoom = false,
                zoomControlsEnabled = false,
                zoomGesturesEnabled = false
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            if (state.meeting.requiredPeopleCount == 0) {
                Marker(
                    state = rememberMarkerState(position = coordinates)
                )
            }
        }
        if (state.meeting.requiredPeopleCount != 0) {
            MapUnknownPlaceContainer()
        }
    }
}

@Composable
private fun MapPlaceComment(meeting: Meeting) {
    if (meeting.requiredPeopleCount == 0) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        ) {
            Text(
                text = meeting.locationInfo.locationDetails,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.footnoteRegular,
                color = MaterialTheme.colors.textColorSecondary,
            )
        }
    }
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
    text = meeting.takeWithYouInfo.postersMotivation,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun Posters(
    meeting: Meeting,
    listener: Listener?
) = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
) {
    items(meeting.takeWithYouInfo.posters) { poster ->
        Poster { listener?.openLink(poster) }
    }
    item { Spacer(modifier = Modifier.width(16.dp)) }
}

@Composable
private fun Poster(
    onClick: () -> Unit
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(start = 16.dp)
) {
    CoilImage(
        imageModel = {
            R.drawable.ic_poster
        },
        modifier = Modifier
            .size(96.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    )
    Text(
        text = "Плакат",
        style = MaterialTheme.typography.caption2Regular,
        color = MaterialTheme.colors.textPrimary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 4.dp)
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
    if (meeting.details.goals.isNotEmpty()) {
        DropdownItem(
            title = "Чего хотим добиться",
            steps = meeting.details.goals
        )
    }
    if (meeting.details.slogans.isNotEmpty()) {
        DropdownItem(
            title = "Лозунги",
            steps = meeting.details.slogans
        )
    }
    if (meeting.details.strategy.isNotEmpty()) {
        DropdownItem(
            title = "Стратегия",
            steps = meeting.details.strategy,
            lastItem = true
        )
    }
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
private fun WillYouGoPeopleCount(meeting: Meeting) {
    val maybeGoText = meeting.getPeopleMaybeGoCount()
    val definitelyGoText = meeting.getPeopleGoCount()
    if (definitelyGoText.isNotEmpty()) {
        Text(
            text = "${definitelyGoText}${maybeGoText}А ты?",
            color = MaterialTheme.colors.textPrimary,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}

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
private fun WillYouGoActionButtons(
    state: State,
    listener: Listener?
) {
    val maybeStatus = state.meeting.reaction.peopleMaybeGoCount.contains(state.user.id)
    val positiveStatus = state.meeting.reaction.peopleGoCount.contains(state.user.id)
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        WillYouGoActionButton(
            text = "Пойду",
            borderColor = getBorderColor(true, positiveStatus, maybeStatus),
            textColor = getTextColor(true, positiveStatus, maybeStatus),
            backgroundColor = getBackgroundColor(true, positiveStatus, maybeStatus)
        ) {
            listener?.setReaction(true)
        }
        Spacer(
            modifier = Modifier.width(24.dp)
        )
        WillYouGoActionButton(
            text = "Мб пойду",
            borderColor = getBorderColor(false, positiveStatus, maybeStatus),
            textColor = getTextColor(false, positiveStatus, maybeStatus),
            backgroundColor = getBackgroundColor(false, positiveStatus, maybeStatus),
        ) {
            listener?.setReaction(false)
        }
    }
}

@Composable
private fun WillYouGoActionButton(
    text: String,
    borderColor: Color,
    textColor: Color,
    backgroundColor: Color,
    onSetReaction: () -> Unit
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
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onSetReaction() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headline,
            color = textColor,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

@Composable
private fun getTextColor(
    isPositiveButton: Boolean,
    positiveStatus: Boolean,
    maybeStatus: Boolean
) = when {
    positiveStatus -> {
        if (isPositiveButton) MaterialTheme.colors.textPositive else MaterialTheme.colors.graphicsTertiary
    }
    maybeStatus -> {
        if (isPositiveButton) MaterialTheme.colors.graphicsTertiary else MaterialTheme.colors.graphicsOrange
    }
    else -> if (isPositiveButton) MaterialTheme.colors.textPositive else MaterialTheme.colors.graphicsOrange
}

@Composable
private fun getBackgroundColor(
    isPositiveButton: Boolean,
    positiveStatus: Boolean,
    maybeStatus: Boolean
) = when {
    positiveStatus -> {
        if (isPositiveButton) MaterialTheme.colors.graphicsGreenTransparent else Color.Transparent
    }
    maybeStatus -> {
        if (isPositiveButton) Color.Transparent else MaterialTheme.colors.backgroundOrange
    }
    else -> Color.Transparent
}

@Composable
private fun getBorderColor(
    isPositiveButton: Boolean,
    positiveStatus: Boolean,
    maybeStatus: Boolean
) = when {
    positiveStatus -> {
        if (isPositiveButton) Color.Transparent else MaterialTheme.colors.backgroundSecondary
    }
    maybeStatus -> {
        if (isPositiveButton) MaterialTheme.colors.backgroundSecondary else Color.Transparent
    }
    else -> if (isPositiveButton) {
        MaterialTheme.colors.textPositive.copy(alpha = ButtonDefaults.OutlinedBorderOpacity)
    } else {
        MaterialTheme.colors.graphicsOrange.copy(alpha = ButtonDefaults.OutlinedBorderOpacity)
    }
}

/**
 * PUBLISH BUTTON
 */
@Composable
private fun PublishButton(
    state: State,
    listener: Listener?,
    modifier: Modifier
) {
    StateButton(
        text = "Опубликовать",
        state = state.createMeetingButtonState,
        onSendRequest = {
            listener?.createMeeting()
        },
        onSuccess = {
            listener?.onBack()
            listener?.onBack()
        },
        modifier = modifier
    )
}

/**
 * PREVIEWS
 */
@Preview(showBackground = true)
@Composable
private fun MeetingMainScreenContent() = MeetingsTheme {
    MeetingMainScreenContent(
        isPreviewMode = false,
        state = State(),
        listener = null
    )
}