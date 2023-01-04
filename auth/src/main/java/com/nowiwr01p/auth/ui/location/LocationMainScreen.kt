package com.nowiwr01p.auth.ui.location

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nowiwr01p.auth.R
import com.nowiwr01p.auth.ui.location.LocationContract.*
import com.nowiwr01p.core.datastore.location.data.Country
import com.nowiwr01p.core.datastore.location.data.Location
import com.nowiwr01p.core_ui.extensions.setSystemUiColor
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.*
import com.nowiwr01p.core_ui.ui.ButtonState.DEFAULT
import org.koin.androidx.compose.getViewModel

@Composable
fun LocationMainScreen(
    countryCode: String,
    navigator: Navigator,
    viewModel: LocationViewModel = getViewModel()
) {
    setSystemUiColor()

    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
        override fun onLocationClick(location: Location) {
            viewModel.setEvent(Event.LocationClick(location))
        }
        override fun onConfirmClick() {
            viewModel.setEvent(Event.ConfirmClick)
        }
        override fun onSearchStateChanged(value: String) {
            viewModel.setEvent(Event.OnSearchStateChanged(value))
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

    LocationMainScreenContent(
        countryCode = countryCode,
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
fun LocationMainScreenContent(
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
        Toolbar(countryCode.isNotEmpty(), listener)
        if (state.loaded) {
            LocationList(state, listener)
        } else {
            CenterScreenProgressBar()
        }
    }

    if (countryCode.isEmpty() && state.loaded && state.selectedLocation == null) {
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
    if (state.selectedLocation != null) {
        ChooseButton(listener, buttonModifier)
    }
    if (state.selectedLocation == null) {
        Search(state, listener, buttonModifier)
    }
}

@Composable
private fun Toolbar(
    selectCityScreen: Boolean,
    listener: Listener?
) = ToolbarTop(
    showElevation = true,
    modifier = Modifier.background(MaterialTheme.colors.mainBackgroundColor),
    title = {
        ToolbarTitle(
            textColor = Color.White,
            title = if (selectCityScreen) "Выберите город" else "Выберите страну"
        )
    },
    backIcon = {
        if (selectCityScreen) {
            ToolbarBackButton { listener?.onBackClick() }
        }
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
private fun ChooseButton(
    listener: Listener?,
    modifier: Modifier
) = StateButton(
    text = "Выбрать",
    state = DEFAULT,
    onSendRequest = { listener?.onConfirmClick() },
    modifier = modifier
        .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
        .clip(RoundedCornerShape(24.dp))
)

@Composable
private fun Search(
    state: State,
    listener: Listener?,
    modifier: Modifier
) = TextField(
    value = state.searchValue,
    onValueChange = {
        listener?.onSearchStateChanged(it)
    },
    colors = TextFieldDefaults.outlinedTextFieldColors(
        unfocusedBorderColor = Color.Transparent,
        disabledBorderColor = Color.Transparent,
        errorBorderColor = Color.Transparent,
        focusedBorderColor = Color.Transparent
    ),
    modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
        .background(
            color = Color.White,
            shape = RoundedCornerShape(24.dp)
        )
        .border(
            border = BorderStroke(
                width = 1.25.dp,
                color = MaterialTheme.colors.graphicsSecondary
            ),
            shape = RoundedCornerShape(24.dp)
        ),
    placeholder = {
        Text(
            text = "Поиск нужного города",
            modifier = Modifier.padding(top = 2.dp)
        )
    },
    leadingIcon = {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = "Show or hide password icon",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
)

/**
 * PREVIEWS
 */
@Preview(showBackground = true)
@Composable
private fun PreviewSearch() = MeetingsTheme {
    LocationMainScreenContent(
        countryCode = "",
        state = State(
            loaded = true,
            locations = listOf(
                Country(false, "Test country"), Country(false, "Test country"),
                Country(false, "Test country"), Country(false, "Test country"),
            )
        ),
        listener = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewFull() = MeetingsTheme {
    LocationMainScreenContent(
        countryCode = "",
        state = State(
            loaded = true,
            selectedLocation = Country(true, "Test country"),
            locations = listOf(
                Country(false, "Test country"), Country(false, "Test country"),
                Country(true, "Test country"), Country(false, "Test country"),
            )
        ),
        listener = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoading() = MeetingsTheme {
    LocationMainScreenContent(
        countryCode = "",
        state = State(),
        listener = null
    )
}