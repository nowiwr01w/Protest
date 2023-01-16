package com.nowiwr01p.meetings.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.extenstion.formatToDateTime
import com.nowiwr01p.core.extenstion.getPeopleGoCountShort
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core.model.CreateMeetingMapType.DRAW_PATH
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.isScrollingUp
import com.nowiwr01p.core_ui.extensions.shadowCard
import com.nowiwr01p.core_ui.extensions.toColor
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.animation.pressedAnimation
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.progress.CenterScreenProgressBar
import com.nowiwr01p.meetings.R
import com.nowiwr01p.meetings.ui.main.MeetingsContract.*
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MeetingsMainScreen(
    navigator: Navigator,
    viewModel: MeetingsViewModel = getViewModel { parametersOf(Color.White) }
) {
    val state = viewModel.viewState.value

    val listener = object : Listener {
        override fun toMap(meeting: Meeting) {
            navigator.meetingsNavigator.navigateToMapDrawPath(DRAW_PATH)
        }
        override fun toMeeting(meeting: Meeting) {
            navigator.meetingsNavigator.navigateToMeetingInfo(false, meeting)
        }
        override fun toCreateMeeting() {
            navigator.meetingsNavigator.navigateToCreateMeeting()
        }
        override fun onCategoryClick(category: Category) {
            viewModel.setEvent(Event.SelectCategory(category))
        }
        override fun showBecomeOrganizerBottomSheet() {
            // TODO
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {

    }

    val lazyListState = rememberLazyListState()

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(state, listener, lazyListState) }
    ) {
        MeetingsMainScreenContent(state, listener, lazyListState)
    }
}

@Composable
private fun MeetingsMainScreenContent(
    state: State,
    listener: Listener?,
    lazyListState: LazyListState
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(state)
    if (state.showProgress) {
        CenterScreenProgressBar()
    } else {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            item { Stories(state) }
            item { MeetingsTitle(state) }
            item { Categories(state, listener) }
            Meetings(state, listener)
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
        .pressedAnimation()
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
private fun Categories(state: State, listener: Listener?) = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
) {
    items(state.categories) { category ->
        CategoryItem(category) {
            listener?.onCategoryClick(category)
        }
    }
    item { Spacer(modifier = Modifier.width(12.dp)) }
}

@Composable
private fun CategoryItem(
    category: Category,
    onItemClick: () -> Unit = {},
) {
    val backgroundColor = if (category.isSelected) {
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
        val textColor = if (category.isSelected) {
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
    if (state.meetings.isEmpty()) {
        item { EmptyListStub(state, listener) }
    } else {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(state.meetings) { meeting ->
            MeetingItem(meeting, listener)
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}


@Composable
private fun MeetingItem(
    meeting: Meeting,
    listener: Listener?
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { listener?.toMeeting(meeting) }
        .padding(vertical = 12.dp, horizontal = 16.dp)
        .background(MaterialTheme.colors.background)
) {
    val (image, categories, title, date, count) = createRefs()

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
        imageModel = { meeting.image }
    )

    val categoriesModifier = Modifier
        .padding(top = 12.dp)
        .constrainAs(categories) {
            width = Dimension.fillToConstraints
            start.linkTo(image.start)
            end.linkTo(image.end)
            top.linkTo(image.bottom)
        }
    MeetingCategories(meeting, categoriesModifier)

    val titleModifier = Modifier
        .padding(top = 4.dp)
        .constrainAs(title) {
            width = Dimension.fillToConstraints
            start.linkTo(image.start)
            end.linkTo(image.end)
            top.linkTo(categories.bottom)
        }
    Text(
        text = meeting.title,
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
        text = meeting.date.formatToDateTime(),
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
        text = meeting.getPeopleGoCountShort(),
        color = MaterialTheme.colors.textColorSecondary,
        style = MaterialTheme.typography.subHeadlineRegular,
        modifier = countModifier
    )
}

/**
 * CATEGORY
 */
@Composable
private fun MeetingCategories(
    meeting: Meeting,
    modifier: Modifier
) = LazyRow(
    modifier = modifier.fillMaxWidth()
) {
    val sorted = meeting.categories.distinctBy { it.textColor }.sortedBy { it.priority }.take(3)
    items(sorted) {
        Category(it)
    }
    item { Spacer(modifier = Modifier.width(8.dp)) }
}

@Composable
internal fun Category(
    category: Category,
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
        .padding(end = 8.dp)
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
 * CREATE MEETING FAB
 */
@Composable
private fun FloatingActionButton(
    state: State,
    listener: Listener?,
    lazyListState: LazyListState
) {
    if (!state.showProgress && state.user.organizer && state.meetings.isNotEmpty()) {
        FloatingActionButton(
            painter = rememberVectorPainter(image = Icons.Filled.Add),
            lazyListState = lazyListState,
        ) {
            listener?.toCreateMeeting()
        }
    }
}

@Composable
private fun FloatingActionButton(
    painter: Painter,
    lazyListState: LazyListState,
    onItemClick: () -> Unit
) = AnimatedVisibility(
    visible = lazyListState.isScrollingUp() || !lazyListState.isScrollInProgress,
    enter = fadeIn() + expandVertically(),
    exit = fadeOut() + shrinkVertically()
) {
    FloatingActionButton(
        shape = RoundedCornerShape(14.dp),
        backgroundColor = MaterialTheme.colors.mainBackgroundColor,
        onClick = { onItemClick() }
    ) {
        Icon(
            painter = painter,
            contentDescription = "Floating Action Button",
            tint = Color.White,
            modifier = Modifier.size(20 .dp)
        )
    }
}

/**
 * NO MEETINGS STUB
 */
@Composable
private fun EmptyListStub(state: State, listener: Listener?) = Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(top = 48.dp)
) {
    Text(
        text = "Тут пусто",
        color = MaterialTheme.colors.textPrimary,
        style = MaterialTheme.typography.title1Bold,
        modifier = Modifier.padding(start = 16.dp)
    )
    val titleText = if (state.user.organizer) {
        "Теперь вы организатор\nДействуйте"
    } else {
        "В этом городе ничего не запланировано\nИсправь это"
    }
    Text(
        text = titleText,
        color = MaterialTheme.colors.textColorSecondary,
        style = MaterialTheme.typography.bodyRegular,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp)
    )
    val buttonText = if (state.user.organizer) "Создать митинг" else "Стать организатором"
    StateButton(
        text = buttonText,
        onSendRequest = {
            if (state.user.organizer) listener?.toCreateMeeting() else listener?.showBecomeOrganizerBottomSheet()
        },
        modifier = Modifier
            .fillMaxWidth()
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
        listener = null,
        lazyListState = rememberLazyListState()
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmpty() = MeetingsTheme {
    MeetingsMainScreenContent(
        state = State(),
        listener = null,
        lazyListState = rememberLazyListState()
    )
}