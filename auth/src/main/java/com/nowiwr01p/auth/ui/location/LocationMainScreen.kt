package com.nowiwr01p.auth.ui.location

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nowiwr01p.auth.R
import com.nowiwr01p.auth.ui.location.LocationContract.*
import com.nowiwr01p.core.datastore.location.data.City
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.core.datastore.location.data.Location
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.subHeadlineRegular
import com.nowiwr01p.core_ui.theme.textColorSecondary
import com.nowiwr01p.core_ui.ui.*
import com.nowiwr01p.core_ui.ui.ButtonState.*
import org.koin.androidx.compose.getViewModel

@Composable
fun LocationMainScreen(
    countryCode: String,
    navigator: Navigator,
    viewModel: LocationViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onLocationClick(location: Location) {
            viewModel.setEvent(Event.LocationClick(location))
        }
        override fun onConfirmClick() {
            viewModel.setEvent(Event.ConfirmClick)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init(countryCode))
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.ConfirmClick -> {
                // TODO()
            }
            is Effect.ShowNextScreen -> {
                when (it.location) {
                    is Country -> navigator.authNavigator.toChooseCity(it.location.name)
                    else -> navigator.navigateToMap()
                }
            }
        }
    }

    ChooseCityMainScreenContent(
        countryCode = countryCode,
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
fun ChooseCityMainScreenContent(
    countryCode: String,
    state: State,
    listener: Listener?
) = ConstraintLayout(
    modifier = Modifier.fillMaxSize(),
) {
    val (content, futureText, button) = createRefs()

    val contentModifier = Modifier
        .fillMaxSize()
        .constrainAs(content) {
            linkTo(top = parent.top, bottom = parent.bottom, start = parent.start, end = parent.end)
        }
    Column(modifier = contentModifier) {
        Toolbar(countryCode)
        if (state.loaded) {
            LocationList(state, listener)
        } else {
            CenterScreenProgressBar()
        }
    }

    if (countryCode.isEmpty()) {
        val futureTextModifier = Modifier
            .padding(horizontal = 16.dp)
            .constrainAs(futureText) {
                linkTo(top = parent.top, bottom = parent.bottom, start = parent.start, end = parent.end)
            }
        Text(
            text = "В будущем список поддерживаемых стран будет становиться больше. " +
                    "Вы можете ускорить процесс, сделав запрос в телеграм канале",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subHeadlineRegular,
            color = MaterialTheme.colors.textColorSecondary,
            modifier = futureTextModifier
        )
    }

    val buttonModifier = Modifier
        .constrainAs(button) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    AnimatedVisibility(
        modifier = buttonModifier,
        visible = state.selectedLocation != null
    ) {
        ChooseButton(listener)
    }
}

@Composable
private fun Toolbar(countryCode: String) = ToolbarTop(
    title = {
        ToolbarTitle(
            title = if (countryCode.isNotEmpty()) "Выберите город" else "Выберите страну"
        )
    }
)

@Composable
private fun LocationList(
    state: State,
    listener: Listener?
) = LazyColumn(
    modifier = Modifier.fillMaxSize()
) {
    items(state.locations) { item ->
        LocationItem(item, listener)
    }
}

@Composable
fun LocationItem(
    item: Location,
    listener: Listener?
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .clickable {
            listener?.onLocationClick(item)
        },
) {
    Text(
        text = item.name,
        modifier = Modifier
            .weight(1f)
            .padding(start = 24.dp, top = 16.dp, bottom = 16.dp),
    )
    if (item.selected) {
        Icon(
            painter = painterResource(R.drawable.ic_selected_location),
            contentDescription = "Select location item",
            modifier = Modifier.padding(end = 24.dp)
        )
    }
}

@Composable
private fun ChooseButton(listener: Listener?) = StateButton(
    text = "Выбрать",
    state = DEFAULT,
    onSendRequest = { listener?.onConfirmClick() },
    modifier = Modifier
        .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
        .clip(RoundedCornerShape(24.dp))
)

/**
 * PREVIEW
 */
@Preview(showBackground = true)
@Composable
private fun PreviewFull() = MeetingsTheme {
    ChooseCityMainScreenContent(
        countryCode = "",
        state = State(
            loaded = true,
            selectedLocation = City(true, "Test city"),
            locations = listOf(
                City(false, "Test city"), City(false, "Test city"),
                City(true, "Test city"), City(false, "Test city"),
            )
        ),
        listener = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoading() = MeetingsTheme {
    ChooseCityMainScreenContent(
        countryCode = "",
        state = State(),
        listener = null
    )
}