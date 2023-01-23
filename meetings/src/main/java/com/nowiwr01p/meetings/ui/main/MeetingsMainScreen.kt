package com.nowiwr01p.meetings.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.extenstion.formatToDateTime
import com.nowiwr01p.core.extenstion.getPeopleGoCountAll
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core.model.CreateMeetingMapType.DRAW_PATH
import com.nowiwr01p.core_ui.extensions.ClickableIcon
import com.nowiwr01p.core_ui.extensions.shadowCard
import com.nowiwr01p.core_ui.extensions.toColor
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.animation.pressedAnimation
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.progress.CenterScreenProgressBar
import com.nowiwr01p.domain.meetings.data.Story
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
        override fun toProfile(editMode: Boolean) {
            navigator.navigateToProfile(editMode)
        }
        override fun onStoryClick(story: Story) {
            viewModel.setEvent(Event.SelectStory(story))
        }
        override fun showBecomeOrganizerBottomSheet() {
            // TODO
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    MeetingsMainScreenContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun MeetingsMainScreenContent(
    state: State,
    listener: Listener?
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(state, listener)
    if (state.showProgress) {
        CenterScreenProgressBar()
    } else {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            item { Stories(state, listener) }
            item { MeetingsTitle() }
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
    state: State,
    listener: Listener?
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(start = 16.dp)
) {
    CoilImage(
        imageModel = {
            state.user.avatar.ifEmpty { R.drawable.hao }
        },
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                listener?.toProfile(false)
            }
    )
    Text(
        text = state.user.name.ifEmpty { "Profile" },
        color = MaterialTheme.colors.textPrimary,
        style = MaterialTheme.typography.subHeadlineRegular,
        modifier = Modifier.padding(start = 16.dp)
    )
    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .clip(CircleShape)
            .clickable {
                listener?.toProfile(true)
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
    Spacer(
        modifier = Modifier.weight(1f)
    )
    ClickableIcon(
        icon = R.drawable.ic_add,
        modifier = Modifier.padding(end = 10.dp),
        onClick = { listener?.toCreateMeeting() }
    )
}

/**
 * STORIES LIST
 */
@Composable
private fun Stories(state: State, listener: Listener?) = LazyRow(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
) {
    itemsIndexed(state.stories) { index, item ->
        Story(index, item, state.user.id) {
            listener?.onStoryClick(item)
        }
    }
    item { Spacer(modifier = Modifier.width(6.dp)) }
}

@Composable
private fun Story(
    index: Int,
    story: Story,
    userId: String,
    onItemClick: () -> Unit
) = Column(
    modifier = Modifier
        .padding(start = if (index == 0) 12.dp else 6.dp, end = 6.dp)
        .width(72.dp)
        .pressedAnimation { onItemClick() }
) {
    val borderColor = if (userId in story.viewers) {
        MaterialTheme.colors.graphicsSecondary.copy(alpha = 0.25f)
    } else {
        Color(0xFFFC4C4C)
    }
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .border(2.dp, borderColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CoilImage(
                imageModel = { story.previewIcon },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }
    }
    Text(
        text = story.previewTitle,
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
private fun MeetingsTitle() = Text(
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
        text = meeting.getPeopleGoCountAll(),
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