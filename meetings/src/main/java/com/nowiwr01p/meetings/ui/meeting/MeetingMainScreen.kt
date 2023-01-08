package com.nowiwr01p.meetings.ui.meeting

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import com.nowiwr01p.core_ui.EffectObserver
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
    Toolbar()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item { TopImage() }
        item { Categories() }
        item { Title() }
        item { Description() }
        item { LocationTitle() }
        item { LocationInfoContainer() }
        item { MapPreview() }
        item { MapPlaceComment() }
        item { TakeWithYouTitle() }
        item { TakeWithYouDetails() }
        item { TakeWithYouList() }
        item { DropdownItems() }
        item { WillYouGoTitle() }
        item { WillYouGoPeopleCount() }
        item { WillYouGoImage() }
        item { WillYouGoActionButtons() }
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
private fun TopImage() = CoilImage(
    imageModel = { R.drawable.navalny },
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
private fun Categories() = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
) {
    item {
        Category(
            text = "Политика",
            textColor = MaterialTheme.colors.graphicsBlue,
            backgroundColor = MaterialTheme.colors.backgroundBlue
        )
    }
    item {
        Category(
            text = "Права человека",
            textColor = MaterialTheme.colors.graphicsRed,
            backgroundColor = MaterialTheme.colors.backgroundRed
        )
    }
    item {
        Category(
            text = "Преступность",
            textColor = MaterialTheme.colors.graphicsGreen,
            backgroundColor = MaterialTheme.colors.backgroundGreen
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
private fun Title() = Text(
    text = "Свободу Навальному",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.title2Bold,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

/**
 * DESCRIPTION
 */
@Composable
private fun Description() = Text(
    text = "Ни для кого не секрет, что в России права человека считаются чем-то, что всегда " +
            "уходит на задний план.\n\n" +
            "Алексей Навальный старался изменить ситуацию, но стал жертвой режима. Его посадили " +
            "в тюрьму за то, что он говорил правду.\n" +
            "Так быть не должно.",
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
private fun LocationInfoContainer() = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
) {
    LocationPlace()
    Spacer(modifier = Modifier.weight(1f))
    LocationDate()
}

@Composable
private fun LocationPlace() = Text(
    text = "Дворцовая площадь",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1
)

@Composable
private fun LocationDate() = Text(
    text = "24.02.2023 17:30",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1
)

@Composable
private fun MapPreview() {
    val coordinates = LatLng(59.938946, 30.314982)
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
private fun MapPlaceComment() = Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxSize()
        .padding(top = 8.dp, start = 16.dp, end = 16.dp),
) {
    Text(
        text = "Рядом со сценой",
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
private fun TakeWithYouDetails() = Text(
    text = "Плакаты, флаги, что угодно\nВот примеры:",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun TakeWithYouList() = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
) {
    items(5) {
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

@Preview(showBackground = true)
@Composable
private fun DropdownItems() = Column(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        .border(
            width = 2.dp,
            color = Color.Black.copy(alpha = 0.45f),
            shape = RoundedCornerShape(16.dp)
        )
) {
    DropdownItem("Чего хотим добиться")
    Separator()
    DropdownItem("Лозунги")
    Separator()
    DropdownItem("Стратегия")
}

@Composable
private fun DropdownItem(text: String) {
    val expanded = remember { mutableStateOf(false) }
    val rotateAnimation by animateFloatAsState(
        targetValue = if (expanded.value) 180f else 0f,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                text = text,
                color = MaterialTheme.colors.textPrimary,
                style = MaterialTheme.typography.bodyRegular,
                modifier = Modifier.padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp),
                onClick = { expanded.value = !expanded.value }
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.KeyboardArrowDown),
                    contentDescription = "Drop down icon",
                    tint = MaterialTheme.colors.graphicsTertiary,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .rotate(rotateAnimation)

                )
            }
        }
        if (expanded.value) {
            StepItem("Путин хуйло")
            StepItem("Мы против коррупции")
            StepItem("Пошёл нахуй, пёс")
            StepItem("Россия без Путина")
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun Separator() = Divider(
    modifier = Modifier.fillMaxWidth(),
    color = Color.Black.copy(alpha = 0.45f),
    thickness = 2.dp
)

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
    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun WillYouGoPeopleCount() = Text(
    text = "725 человек точно пойдут\nЕщё 1322, возможно, тоже\nА ты?",
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
private fun WillYouGoActionButtons() = Row(
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