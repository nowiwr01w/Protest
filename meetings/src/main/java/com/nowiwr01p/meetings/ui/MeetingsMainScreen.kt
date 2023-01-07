package com.nowiwr01p.meetings.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.setSystemUiColor
import com.nowiwr01p.core_ui.extensions.shadowCard
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.progress.CenterScreenProgressBar
import com.nowiwr01p.domain.meetings.data.Category
import com.nowiwr01p.meetings.R
import com.nowiwr01p.meetings.ui.MeetingsContract.*
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun MeetingsMainScreen(
    navigator: Navigator,
    viewModel: MeetingsViewModel = getViewModel()
) {
    setSystemUiColor(Color.White)

    val state = viewModel.viewState.value

    val listener = object : Listener {
        override fun toCreateMeeting() {
            // TODO
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {

    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { CreateButton(state, listener) }
    ) {
        MeetingsMainScreenContent(state, listener)
    }
}

@Composable
private fun MeetingsMainScreenContent(
    state: State,
    listener: Listener?
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(state)
    if (state.showProgress) {
        CenterScreenProgressBar()
    } else {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            item { Stories(state) }
            item { MeetingsTitle(state) }
            item { Categories(state, listener) }
            if (state.meetings.isEmpty()) {
                item { EmptyListStub() }
            } else {
                Meetings(state, listener)
            }
        }
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(
    state: State
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
) {
    Image(
        painter = painterResource(R.drawable.zero_two),
        contentDescription = "Icon user",
        modifier = Modifier
            .padding(start = 16.dp)
            .size(32.dp)
            .clip(CircleShape)
    )
    Text(
        text = state.user.email,
        color = MaterialTheme.colors.textPrimary,
        style = MaterialTheme.typography.subHeadlineRegular,
        modifier = Modifier.padding(start = 16.dp)
    )
    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .clip(CircleShape)
            .clickable {

            }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = "Icon edit",
            modifier = Modifier
                .padding(4.dp)
                .size(16.dp)
        )
    }
}

/**
 * STORIES LIST
 */
@Composable
private fun Stories(
    state: State
) = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
) {
    items(6) { index ->
        Story(state, index)
    }
    item { Spacer(modifier = Modifier.width(6.dp)) }
}

@Composable
private fun Story(
    state: State,
    index: Int
) = Column(
    modifier = Modifier
        .padding(start = if (index == 0) 12.dp else 6.dp, end = 6.dp)
        .width(72.dp)
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .border(2.dp, Color(0xFFFC4C4C), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.hao),
                contentDescription = "Story item icon",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }
    }
    Text(
        text = "Важная штука",
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.textPrimary,
        style = MaterialTheme.typography.captionRegular,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    )
}

/**
 * MEETINGS TITLE
 */
@Composable
private fun MeetingsTitle(state: State) = Text(
    text = "Ближайшие митинги",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.title2Bold,
    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
)

/**
 * CATEGORIES LIST
 */
@Composable
private fun Categories(
    state: State,
    listener: Listener?
) = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
) {
    items(state.categories) { category ->
        Category(category, state, listener)
    }
    item { Spacer(modifier = Modifier.width(12.dp)) }
}

@Composable
private fun Category(
    category: Category,
    state: State,
    listener: Listener?,
    isSelected: Boolean = false,
    onItemClick: () -> Unit = {},
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colors.backgroundSpecialInverse
    } else {
        MaterialTheme.colors.backgroundSpecial
    }
    Card(
        backgroundColor = backgroundColor,
        modifier = Modifier
            .padding(start = 12.dp)
            .shadowCard(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = true,
                backgroundColor = backgroundColor
            )
            .clickable { onItemClick() },
    ) {
        val textColor = if (isSelected) {
            MaterialTheme.colors.textPrimaryInverted
        } else {
            MaterialTheme.colors.textPrimary
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.subHeadlineRegular,
                color = textColor,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

/**
 * MEETINGS
 */
private fun LazyListScope.Meetings(
    state: State,
    listener: Listener?
) {
    item { Spacer(modifier = Modifier.height(8.dp)) }
    items(5) {
        MeetingItem(state, listener)
    }
    item { Spacer(modifier = Modifier.height(8.dp)) }
}


@Composable
private fun MeetingItem(
    state: State,
    listener: Listener?
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clickable {  }
        .padding(vertical = 12.dp, horizontal = 16.dp)
        .background(MaterialTheme.colors.background)
) {
    val (image, title, date, count) = createRefs()

    val imageModifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(16.dp))
        .constrainAs(image) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    CoilImage(
        modifier = imageModifier,
        imageModel = { R.drawable.navalny }
    )

    val titleModifier = Modifier
        .padding(top = 8.dp)
        .constrainAs(title) {
            width = Dimension.fillToConstraints
            start.linkTo(image.start)
            end.linkTo(image.end)
            top.linkTo(image.bottom)
        }
    Text(
        text = "Свободу Навальному",
        color = MaterialTheme.colors.textPrimary,
        style = MaterialTheme.typography.title2Bold,
        modifier = titleModifier
    )

    val dateModifier = Modifier
        .padding(top = 4.dp)
        .constrainAs(date) {
            start.linkTo(title.start)
            top.linkTo(title.bottom)
        }
    Text(
        text = "24.02.2023 17:30",
        color = MaterialTheme.colors.textColorSecondary,
        style = MaterialTheme.typography.subHeadlineRegular,
        modifier = dateModifier
    )

    val countModifier = Modifier
        .constrainAs(count) {
            end.linkTo(parent.end)
            top.linkTo(date.top)
            bottom.linkTo(date.bottom)
        }
    Text(
        text = "175 человек",
        color = MaterialTheme.colors.textColorSecondary,
        style = MaterialTheme.typography.subHeadlineRegular,
        modifier = countModifier
    )
}

/**
 * CREATE MEETING FAB
 */
@Composable
private fun CreateButton(
    state: State,
    listener: Listener?
) {
    if (!state.showProgress && state.user.organizer) {
        FloatingActionButton(
            contentColor = Color.White,
            backgroundColor = MaterialTheme.colors.mainBackgroundColor,
            onClick = { listener?.toCreateMeeting() }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Create new meeting button"
            )
        }
    }
}

/**
 * NO MEETINGS STUB
 */
@Composable
private fun EmptyListStub() = Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(top = 48.dp)
) {
    Image(
        painter = painterResource(R.drawable.image_no_meetings),
        contentDescription = "No meetings image stub"
    )
    Text(
        text = "Тут пусто",
        color = MaterialTheme.colors.textPrimary,
        style = MaterialTheme.typography.title1Bold,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
    )
    Text(
        text = "В этом городе ничего не запланировано\nИсправь это",
        color = MaterialTheme.colors.textColorSecondary,
        style = MaterialTheme.typography.bodyRegular,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp)
    )
    StateButton(
        text = "Стать организатором",
        modifier = Modifier
            .padding(top = 32.dp, start = 48.dp, end = 48.dp)
            .clip(RoundedCornerShape(24.dp))
    )
}

/**
 * PREVIEWS
 */
@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    MeetingsMainScreenContent(
        state = State(
            meetings = listOf(Meeting())
        ),
        listener = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmpty() = MeetingsTheme {
    MeetingsMainScreenContent(
        state = State(),
        listener = null
    )
}