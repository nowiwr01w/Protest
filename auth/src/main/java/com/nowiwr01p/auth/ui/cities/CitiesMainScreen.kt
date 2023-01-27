package com.nowiwr01p.auth.ui.cities

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nowiwr01p.auth.R
import com.nowiwr01p.auth.ui.cities.CitiesContract.*
import com.nowiwr01p.core.datastore.cities.data.City
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.graphicsSecondary
import com.nowiwr01p.core_ui.theme.mainBackgroundColor
import com.nowiwr01p.core_ui.ui.button.ButtonState.DEFAULT
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.progress.CenterScreenProgressBar
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import org.koin.androidx.compose.getViewModel

@Composable
fun CitiesMainScreen(
    fromProfile: Boolean,
    fromCreateMeeting: Boolean,
    navigator: Navigator,
    viewModel: CitiesViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
        override fun onCityClick(city: City) {
            viewModel.setEvent(Event.CityClick(city))
        }
        override fun onConfirmClick() {
            viewModel.setEvent(Event.ConfirmClick)
        }
        override fun onSearchStateChanged(value: String) {
            viewModel.setEvent(Event.OnSearchStateChanged(value))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init(fromCreateMeeting))
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.ShowNextScreen -> {
                if (fromProfile || fromCreateMeeting) {
                    if (fromCreateMeeting) {
                        navigator.setScreenResult(arg, it.path)
                        navigator.navigateUp()
                    }
                    navigator.navigateUp()
                } else {
                    navigator.navigateToMeetings()
                }
            }
        }
    }

    CitiesMainScreenContent(
        fromProfile = fromProfile,
        state = viewModel.viewState.value,
        listener = listener
    )
}

/**
 * CONTENT
 */
@Composable
fun CitiesMainScreenContent(
    fromProfile: Boolean,
    state: State,
    listener: Listener?
) = ConstraintLayout(
    modifier = Modifier.fillMaxSize(),
) {
    val (toolbar, cities, button, progress) = createRefs()

    val toolbarModifier = Modifier
        .constrainAs(toolbar) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    Toolbar(
        fromProfile = fromProfile,
        listener = listener,
        modifier = toolbarModifier,
    )

    if (state.loaded) {
        val citiesModifier = Modifier
            .constrainAs(cities) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(toolbar.bottom)
            }
        CitiesList(
            state = state,
            listener = listener,
            modifier = citiesModifier
        )

        val buttonModifier = Modifier
            .constrainAs(button) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        if (state.selectedCity != null) {
            ChooseButton(listener, buttonModifier)
        } else {
            Search(state, listener, buttonModifier)
        }
    } else {
        val progressModifier = Modifier
            .constrainAs(progress) {
                top.linkTo(toolbar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        CenterScreenProgressBar(progressModifier)
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(
    fromProfile: Boolean,
    listener: Listener?,
    modifier: Modifier,
) {
    ToolbarTop(
        showElevation = true,
        blackColors = !fromProfile,
        modifier = modifier.background(MaterialTheme.colors.mainBackgroundColor),
        title = {
            ToolbarTitle(
                textColor = if (fromProfile) Color.Black else Color.White,
                title = "Выберите город"
            )
        },
        backIcon = {
            ToolbarBackButton(blackColors = !fromProfile) {
                listener?.onBackClick()
            }
        }
    )
}

/**
 * LOCATIONS
 */
@Composable
private fun CitiesList(
    state: State,
    listener: Listener?,
    modifier: Modifier
) {
    LazyColumn(modifier) {
        items(state.cities) { item ->
            CityItem(item, listener)
        }
        item { Spacer(modifier = Modifier.height(156.dp)) }
    }
}

@Composable
fun CityItem(
    item: City,
    listener: Listener?
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .clickable {
            listener?.onCityClick(item)
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

/**
 * BOTTOM BUTTON
 */
@Composable
private fun ChooseButton(
    listener: Listener?,
    modifier: Modifier
) = StateButton(
    text = "Выбрать",
    state = DEFAULT,
    onSendRequest = { listener?.onConfirmClick() },
    modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
        .clip(RoundedCornerShape(24.dp))
)

/**
 * BOTTOM CITIES SEARCH
 */
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
 * CITIES PREVIEWS
 */
@Preview(showBackground = true)
@Composable
private fun PreviewCitiesLoaded() = MeetingsTheme {
    CitiesMainScreenContent(
        fromProfile = false,
        state = State(
            loaded = true,
            cities = listOf(
                City(false, "Test city"), City(false, "Test city"),
                City(false, "Test city"), City(false, "Test city"),
            )
        ),
        listener = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCitySelected() = MeetingsTheme {
    CitiesMainScreenContent(
        fromProfile = false,
        state = State(
            loaded = true,
            selectedCity = City(true, "Test city"),
            cities = listOf(
                City(false, "Test city"), City(false, "Test city"),
                City(true, "Test city"), City(false, "Test city"),
            )
        ),
        listener = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCitiesLoading() = MeetingsTheme {
    CitiesMainScreenContent(
        fromProfile = false,
        state = State(),
        listener = null
    )
}