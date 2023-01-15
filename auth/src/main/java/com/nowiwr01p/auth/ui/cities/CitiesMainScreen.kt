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
import com.nowiwr01p.core_ui.extensions.setSystemUiColor
import com.nowiwr01p.core_ui.ui.progress.CenterScreenProgressBar
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.button.ButtonState.DEFAULT
import com.nowiwr01p.core_ui.ui.button.StateButton
import org.koin.androidx.compose.getViewModel

@Composable
fun CitiesMainScreen(
    navigator: Navigator,
    viewModel: CitiesViewModel = getViewModel()
) {
    setSystemUiColor()

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
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.ConfirmClick -> {
                // TODO()
            }
            is Effect.ShowNextScreen -> {
                when (it.city) {
                    else -> navigator.navigateToMeetings()
                }
            }
        }
    }

    CitiesMainScreenContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

/**
 * CONTENT
 */
@Composable
fun CitiesMainScreenContent(
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
    listener: Listener?,
    modifier: Modifier,
) = ToolbarTop(
    showElevation = true,
    modifier = modifier.background(MaterialTheme.colors.mainBackgroundColor),
    title = {
        ToolbarTitle(
            textColor = Color.White,
            title = "Выберите город"
        )
    },
    backIcon = {
        ToolbarBackButton { listener?.onBackClick() }
    }
)

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
        state = State(),
        listener = null
    )
}