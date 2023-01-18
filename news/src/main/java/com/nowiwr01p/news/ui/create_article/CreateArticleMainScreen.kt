package com.nowiwr01p.news.ui.create_article

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nowiwr01p.core.model.*
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.ClickableIcon
import com.nowiwr01p.core_ui.extensions.ReversedRow
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.bodyRegular
import com.nowiwr01p.core_ui.theme.graphicsSecondary
import com.nowiwr01p.core_ui.theme.subHeadlineRegular
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.news.ui.create_article.CreateArticleContract.*
import com.nowiwr01p.news.ui.create_article.data.CreateArticleDataType
import com.nowiwr01p.news.ui.create_article.data.CreateArticleDataType.*
import com.nowiwr01p.news.ui.create_article.data.CreateArticleBottomSheetType
import com.nowiwr01p.news.ui.create_article.data.DynamicFields
import com.nowiwr01p.news.ui.create_article.data.StaticFields
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun CreateArticleMainScreen(
    navigator: Navigator,
    viewModel: CreateArticleViewModel = getViewModel()
) {
    val addFieldBottomSheetParams = BottomSheetParams(
        content = AddFieldBottomSheet { viewModel.setEvent(Event.OnBottomSheetTypeClick(it)) }
    )

    val listener = object : Listener {
        override fun onBackClick() {
            viewModel.setEvent(Event.NavigateBack)
        }
        override fun showBottomSheet() {
            viewModel.setEvent(Event.ShowBottomSheet(addFieldBottomSheetParams))
        }
        override fun onStaticFieldChanged(type: StaticFields, value: String) {
            viewModel.setEvent(Event.OnStaticFieldChanged(type, value))
        }
        override fun onDynamicFieldChanged(index: Int, subIndex: Int, type: DynamicFields, value: String) {
            viewModel.setEvent(Event.OnDynamicFieldChanged(index, subIndex, type, value))
        }
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.NavigateBack -> {
                navigator.navigateUp()
            }
        }
    }

    CreateArticleMainScreenContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

/**
 * SCREEN CONTENT
 */
@Composable
private fun CreateArticleMainScreenContent(state: State, listener: Listener?) = ConstraintLayout(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
) {
    val (toolbar, content, button) = createRefs()

    val toolbarModifier = Modifier
        .fillMaxWidth()
        .constrainAs(toolbar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    Toolbar(
        listener = listener,
        modifier = toolbarModifier,
    )

    val contentModifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
        .constrainAs(content) {
            height = Dimension.fillToConstraints
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(toolbar.bottom)
            bottom.linkTo(parent.bottom)
        }
    LazyColumn(modifier = contentModifier) {
        items(state.content) { item ->
            when (item) {
                is TopImage -> TopImageItem(state, listener).toItem()
                is Title -> TitleItem(state, listener).toItem()
                is Description -> DescriptionItem(state, listener).toItem()
                is SubTitle -> SubTitleItem(state, listener, item.order).toItem()
                is ImageList -> ImageList(state, listener, item)
                is OrderedList -> OrderedList(state, listener, item)
                else -> {}
            }
        }
        item { Spacer(modifier = Modifier.height(120.dp)) }
    }

    val buttonModifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
        .clip(RoundedCornerShape(24.dp))
        .constrainAs(button) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    StateButton(
        text = "Добавить поле",
        onSendRequest = { listener?.showBottomSheet() },
        modifier = buttonModifier
    )
}

@Composable
fun CreateArticleDataType.toItem() = CustomTextField(this)

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(
    listener: Listener?,
    modifier: Modifier
) {
    ToolbarTop(
        backIcon = {
            ToolbarBackButton {
                listener?.onBackClick()
            }
        },
        title = {
            ToolbarTitle(title = "Создание новости")
        },
        modifier = modifier
    )
}

/**
 * TEXT FIELD
 */
@Composable
private fun CustomTextField(item: CreateArticleDataType) = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    if (item.showSubItemSlash) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "—",
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
    }
    TextField(
        value = item.value,
        onValueChange = { item.onValueChanged(it) },
        label = {
            Text(
                text = item.hint,
                modifier = Modifier.padding(top = 3.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .border(
                border = BorderStroke(
                    width = 1.25.dp,
                    color = MaterialTheme.colors.graphicsSecondary
                ),
                shape = RoundedCornerShape(12.dp)
            ),
        trailingIcon = if (item.trailingIconCallback == null) null else {
            {
                ClickableIcon(
                    icon = Icons.Default.Close,
                    modifier = Modifier.padding(end = 12.dp),
                    onClick = { item.trailingIconCallback!!.invoke() }
                )
            }
        }
    )
}

/**
 * ADD FIELD BOTTOM SHEET
 */
@Composable
private fun AddFieldBottomSheet(onTypeClick: (CreateArticleBottomSheetType) -> Unit): @Composable () -> Unit = {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CreateArticleBottomSheetType.values().forEach { type ->
            AddFieldBottomSheetItem(type) { onTypeClick.invoke(type) }
        }
    }
}

@Composable
private fun AddFieldBottomSheetItem(
    type: CreateArticleBottomSheetType,
    onTypeClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTypeClick() },
    ) {
        Text(
            text = type.type,
            color = Color.Black,
            style = MaterialTheme.typography.bodyRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
        )
    }
}

/**
 * IMAGE LIST
 */
@Composable
private fun ImageList(
    state: State,
    listener: Listener?,
    item: ImageList
) {
    ExpandableItems("Картинки") {
        item.images.forEachIndexed { index, _ ->
            ImageLinkItem(
                state = state,
                listener = listener,
                order = item.order,
                imageIndex = index
            ).toItem()
            ImageDescriptionItem(
                state = state,
                listener = listener,
                order = item.order,
                imageIndex = index
            ).toItem()
        }
    }
}

/**
 * ORDERED LIST
 */
@Composable
private fun OrderedList(
    state: State,
    listener: Listener?,
    item: OrderedList
) {
    ExpandableItems("Список") {
        ItemOrderedListTitle(
            state = state,
            listener = listener,
            order = item.order
        ).toItem()
        item.steps.forEachIndexed { index, _ ->
            ItemOrderedListItem(
                state = state,
                listener = listener,
                stepIndex = index,
                order = item.order
            ).toItem()
        }
    }
}

/**
 * EXPANDABLE ITEMS
 */
@Composable
private fun ExpandableItems(
    title: String,
    contentBuilder: @Composable () -> Unit
) {
    Column {
        Header(title)
        Column(
            modifier = Modifier.animateContentSize()
        ) {
            contentBuilder()
        }
    }
}

@Composable
private fun Header(title: String) = ReversedRow(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(top = 16.dp)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(56.dp)
            .border(
                border = BorderStroke(
                    width = 1.25.dp,
                    color = MaterialTheme.colors.graphicsSecondary
                ),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        ClickableIcon(icon = Icons.Default.AddCircleOutline) {
            // TODO
        }
    }
    Spacer(
        modifier = Modifier.width(16.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                border = BorderStroke(
                    width = 1.25.dp,
                    color = MaterialTheme.colors.graphicsSecondary
                ),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            text = title,
            color = Color(0x99000000),
            style = MaterialTheme.typography.subHeadlineRegular,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

/**
 * PREVIEWS
 */
@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    CreateArticleMainScreenContent(
        state = State(),
        listener = null
    )
}