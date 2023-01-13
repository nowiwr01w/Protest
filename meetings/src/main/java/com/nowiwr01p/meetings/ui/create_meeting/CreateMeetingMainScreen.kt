package com.nowiwr01p.meetings.ui.create_meeting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.*
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.MeetingsTheme
import com.nowiwr01p.core_ui.theme.bodyRegular
import com.nowiwr01p.core_ui.theme.graphicsSecondary
import com.nowiwr01p.core_ui.theme.subHeadlineRegular
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.*
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.State
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType
import com.nowiwr01p.meetings.ui.create_meeting.data.DetailsItemType.*
import com.nowiwr01p.core.model.CreateMeetingMapType.*
import com.nowiwr01p.core_ui.NavKeys.NAV_ARG_PATH
import com.nowiwr01p.core_ui.NavKeys.NAV_ARG_START_LOCATION
import com.nowiwr01p.meetings.ui.main.Category
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateMeetingMainScreen(
    navigator: Navigator,
    viewModel: CreateMeetingVewModel = getViewModel()
) {
    val state = viewModel.viewState.value

    val categoriesBottomSheet = CategoriesBottomSheet(state) {
        viewModel.setEvent(Event.OnSelectedCategoryClick(it))
    }

    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
        override fun onAddDetailsItem(type: DetailsItemType) {
            viewModel.setEvent(Event.OnAddDetailsItemClick(type))
        }
        override fun onRemoveDetailsType(type: DetailsItemType, index: Int) {
            viewModel.setEvent(Event.OnRemoveDetailsItemClick(type, index))
        }
        override fun showCategoriesBottomSheet() {
            viewModel.setEvent(Event.ShowCategoriesBottomSheet(categoriesBottomSheet))
        }
        override fun navigateToMapDrawPath() {
            viewModel.setEvent(Event.NavigateToMapDrawPath)
        }
        override fun navigateChooseStartLocation() {
            viewModel.setEvent(Event.NavigateToChooseStartLocation)
        }
        override fun showDateTimePicker() {
            viewModel.setEvent(Event.ShowDateTimePicker)
        }
        override fun onDateSelected(date: String) {
            viewModel.setEvent(Event.SelectDate(date))
        }
        override fun onTimeSelected(time: String) {
            viewModel.setEvent(Event.SelectTime(time))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.NavigateToMapDrawPath -> {
                navigator.meetingsNavigator.navigateToMapDrawPath(DRAW_PATH)
            }
            is Effect.NavigateToChooseStartLocation -> {
                navigator.meetingsNavigator.navigateToMapDrawPath(SELECT_START_LOCATION)
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(true) {
        /** DRAW PATH **/
        val pathLiveData = navigator.getLiveDataResult<List<LatLng>>(NAV_ARG_PATH)
        val pathObserver = Observer<List<LatLng>> {
            if (it.isNotEmpty()) viewModel.setEvent(Event.SetDrawnPath(it))
        }
        pathLiveData?.observe(lifecycleOwner, pathObserver)

        /** SELECT START LOCATION **/
        val startLocationLiveData = navigator.getLiveDataResult<List<LatLng>>(NAV_ARG_START_LOCATION)
        val startLocationObserver = Observer<List<LatLng>> {
            if (it.isNotEmpty()) viewModel.setEvent(Event.SetStartLocationPath(it.first()))
        }
        startLocationLiveData?.observe(lifecycleOwner, startLocationObserver)

        /** DISPOSE **/
        onDispose {
            pathLiveData?.removeObserver(pathObserver)
            startLocationLiveData?.removeObserver(startLocationObserver)
        }
    }

    if (state.showDatePicker) {
        DatePicker(listener)
    }
    if (state.showTimePicker) {
        TimePicker(listener)
    }
    CreateMeetingScreenContent(
        state = state,
        listener = listener
    )
}

@Composable
private fun CreateMeetingScreenContent(
    state: State,
    listener: Listener?
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
) {
    Toolbar(listener)
    Content(state, listener)
}

@Composable
private fun Content(
    state: State,
    listener: Listener?
) = ConstraintLayout(
    modifier = Modifier.fillMaxWidth()
) {
    val (fieldsContainer, createButton) = createRefs()

    val fieldsContainerModifier = Modifier
        .fillMaxSize()
        .constrainAs(fieldsContainer) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    FieldsContainer(
        state = state,
        listener = listener,
        modifier = fieldsContainerModifier
    )

    val createButtonModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
        .clip(RoundedCornerShape(24.dp))
        .constrainAs(createButton) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    StateButton(
        text = "Создать",
        modifier = createButtonModifier
    )
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(listener: Listener?) = ToolbarTop(
    title = {
        ToolbarTitle(title = "Создание митинга")
    },
    backIcon = {
        ToolbarBackButton { listener?.onBackClick() }
    }
)

/**
 * FIELDS TO FILL
 */
@Composable
private fun FieldsContainer(
    state: State,
    listener: Listener?,
    modifier: Modifier
) = LazyColumn(
    modifier = modifier
        .padding(horizontal = 16.dp)
        .animateContentSize()
) {
    item { CustomTextField(hint = "Ссылка на картинку") }
    item { Categories(state, listener) }
    item { CustomTextField(hint = "Название") }
    item { CustomTextField(hint = "Описание") }
    item { Date(state, listener) }
    item { OpenDate(state, listener) }
    item { CustomTextField(hint = "Ссылка на чат в Telegram") }
    item { CustomTextField(hint = "Мотивация идти с плакатами") }
    item { Posters(state, listener) }
    item { Goals(state, listener) }
    item { Slogans(state, listener) }
    item { Strategy(state, listener) }
    item { Spacer(modifier = Modifier.height(120.dp)) }
}

/**
 * TEXT FIELD
 */
@Preview(showBackground = true)
@Composable
private fun CustomTextField(
    hint: String = "Тестик",
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    showSubItemSlash: Boolean = false,
    trailingIconCallback: (() -> Unit)? = null
) = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    if (showSubItemSlash) {
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
        value = "",
        onValueChange = {

        },
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        label = {
            Text(
                text = hint,
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
        trailingIcon = {
            if (trailingIconCallback != null) {
                ClickableIcon(
                    icon = Icons.Default.Close,
                    modifier = Modifier.padding(end = 12.dp),
                    onClick = { trailingIconCallback.invoke() }
                )
            }
        }
    )
}

/**
 * CATEGORIES
 */
@Composable
private fun Categories(
    state: State,
    listener: Listener?
) {
    FakeTextField(
        onClick = { listener?.showCategoriesBottomSheet() }
    ) {
        if (state.selectedCategories.isEmpty()) {
            CategoriesStub()
        } else {
            CategoriesList(state)
        }
    }
}

@Composable
private fun CategoriesStub() = Text(
    text = "Категории",
    color = Color(0x99000000),
    style = MaterialTheme.typography.subHeadlineRegular,
    modifier = Modifier.padding(start = 16.dp)
)

@Composable
private fun CategoriesList(state: State) = LazyRow(
    modifier = Modifier.fillMaxSize(),
    verticalAlignment = Alignment.CenterVertically
) {
    val selectedCategories = state.selectedCategories.toList().sortedBy { it.backgroundColor }

    item { Spacer(modifier = Modifier.width(16.dp)) }
    items(selectedCategories) {
        Category(it)
    }
    item { Spacer(modifier = Modifier.width(16.dp)) }
}

/**
 * CATEGORIES BOTTOM SHEET
 */
@Composable
private fun CategoriesBottomSheet(
    state: State,
    onCategoryClick: (category: Category) -> Unit
): @Composable () -> Unit = {
    val maxHeight = LocalConfiguration.current.screenHeightDp.dp - 232.dp
    LazyColumn(
        modifier = Modifier.heightIn(max = maxHeight)
    ) {
        items(state.categories) { category ->
            CategoriesBottomSheetItem(state, category) {
                onCategoryClick.invoke(category)
            }
        }
    }
}

@Composable
private fun CategoriesBottomSheetItem(
    state: State,
    category: Category,
    onCategoryClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCategoryClick() },
    ) {
        Text(
            text = category.name,
            color = Color.Black,
            style = MaterialTheme.typography.bodyRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        CheckBox(checked = state.selectedCategories.contains(category)) {
            onCategoryClick()
        }
        Spacer(
            modifier = Modifier.width(16.dp)
        )
    }
}

/**
 * DATE & LOCATION
 */
@Composable
private fun Date(
    state: State,
    listener: Listener?
) {
    val checked = rememberSaveable { mutableStateOf(false) }
    Column {
        DateMainItem(checked)
        AnimatedVisibility(visible = checked.value) {
            Column {
                DateTimeSubItem(state, listener)
                ChooseStartLocationItem(state, listener)
                CustomTextField(hint = "Название места встречи", showSubItemSlash = true)
                CustomTextField(hint = "Детали места встречи", showSubItemSlash = true)
                DrawPathItem(state, listener)
            }
        }
    }
}

@Composable
private fun DateMainItem(checked: MutableState<Boolean>) = FakeTextField(
    onClick = { checked.value = !checked.value }
) {
    Text(
        text = "Дата и локация",
        color = Color(0x99000000),
        style = MaterialTheme.typography.subHeadlineRegular,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
private fun DateTimeSubItem(
    state: State,
    listener: Listener?
) = FakeTextField(
    showSubItemSlash = true,
    onClick = { listener?.showDateTimePicker() }
) {
    Text(
        text = (state.selectedDate + " " + state.selectedTime).trim().ifEmpty { "Дата и время" },
        color = Color(0x99000000),
        style = MaterialTheme.typography.subHeadlineRegular,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
private fun ChooseStartLocationItem(
    state: State,
    listener: Listener?
) {
    FakeTextField(
        showSubItemSlash = true,
        onClick = { listener?.navigateChooseStartLocation() }
    ) {
        Text(
            text = if (state.startLocation.latitude == .0) "Место встречи" else "Место встречи сохранено",
            color = Color(0x99000000),
            style = MaterialTheme.typography.subHeadlineRegular,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun DrawPathItem(
    state: State,
    listener: Listener?
) {
    FakeTextField(
        showSubItemSlash = true,
        onClick = { listener?.navigateToMapDrawPath() }
    ) {
        Text(
            text = if (state.path.isEmpty()) "Путь" else "Путь сохранён",
            color = Color(0x99000000),
            style = MaterialTheme.typography.subHeadlineRegular,
            maxLines = 1,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

/**
 * OPEN DATE
 */
@Composable
private fun OpenDate(
    state: State,
    listener: Listener?
) {
    val checked = rememberSaveable { mutableStateOf(false) }
    Column {
        ExpandableItem("Открытая дата") {
            CheckBox(checked.value) { checked.value = !checked.value }
        }
        AnimatedVisibility(visible = checked.value) {
            CustomTextField(
                hint = "Сколько людей должно пойти",
                keyboardType = KeyboardType.Number,
                showSubItemSlash = true
            )
        }
    }
}

/**
 * POSTERS
 */
@Composable
private fun Posters(
    state: State,
    listener: Listener?
) {
    ExpandableItems(
        title = "Ссылки на плакаты",
        items = state.posters,
        onAddItem = { listener?.onAddDetailsItem(POSTER_LINKS) },
        onRemoveItem = { listener?.onRemoveDetailsType(POSTER_LINKS, it) }
    )
}

/**
 * GOALS
 */
@Composable
private fun Goals(
    state: State,
    listener: Listener?
) {
    ExpandableItems(
        title = "Цели",
        items = state.goals,
        onAddItem = { listener?.onAddDetailsItem(GOALS) },
        onRemoveItem = { listener?.onRemoveDetailsType(GOALS, it) }
    )
}

/**
 * SLOGANS
 */
@Composable
private fun Slogans(
    state: State,
    listener: Listener?
) {
    ExpandableItems(
        title = "Лозунги",
        items = state.slogans,
        onAddItem = { listener?.onAddDetailsItem(SLOGANS) },
        onRemoveItem = { listener?.onRemoveDetailsType(SLOGANS, it) }
    )
}

/**
 * STRATEGY
 */
@Composable
private fun Strategy(
    state: State,
    listener: Listener?
) {
    ExpandableItems(
        title = "Стратегия",
        items = state.strategy,
        onAddItem = { listener?.onAddDetailsItem(STRATEGY) },
        onRemoveItem = { listener?.onRemoveDetailsType(STRATEGY, it) }
    )
}

/**
 * FAKE TEXT FIELD
 */
@Composable
private fun FakeTextField(
    onClick: () -> Unit,
    showSubItemSlash: Boolean = false,
    content: @Composable () -> Unit
) = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    if (showSubItemSlash) {
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                border = BorderStroke(
                    width = 1.25.dp,
                    color = MaterialTheme.colors.graphicsSecondary
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
    ) {
        content()
    }
}

/**
 * EXPANDABLE ITEMS
 */
@Composable
private fun ExpandableItems(
    title: String,
    items: List<*>,
    onRemoveItem: (index: Int) -> Unit,
    onAddItem: () -> Unit = {},
) {
    val isVisible = rememberSaveable { mutableStateOf(false) }
    Column {
        ExpandableItem(title) {
            ClickableIcon(icon = Icons.Default.AddCircleOutline) {
                isVisible.value = true
                onAddItem.invoke()
            }
        }
        AnimatedVisibility(visible = isVisible.value) {
            Column(
                modifier = Modifier.animateContentSize()
            ) {
                items.forEachIndexed { index, _ ->
                    CustomTextField(hint = "$index", showSubItemSlash = true) {
                        onRemoveItem.invoke(index)
                        if (items.isEmpty()) isVisible.value = false
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandableItem(
    hint: String,
    Icon: @Composable () -> Unit
) = ReversedRow(
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
        Icon()
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
            text = hint,
            color = Color(0x99000000),
            style = MaterialTheme.typography.subHeadlineRegular,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

/**
 * PREVIEW
 */
@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    CreateMeetingScreenContent(
        state = State(),
        listener = null
    )
}