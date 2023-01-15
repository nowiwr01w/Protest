@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.nowiwr01p.core.extenstion.getFromPicker
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.core.model.CreateMeetingMapType.*
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.NavKeys.NAV_ARG_PATH
import com.nowiwr01p.core_ui.NavKeys.NAV_ARG_START_LOCATION
import com.nowiwr01p.core_ui.extensions.*
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.bottom_sheet.BottomSheetParams
import com.nowiwr01p.core_ui.ui.button.StateButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.domain.cteate_meeting.validators.data.CreateMeetingFieldItemType
import com.nowiwr01p.domain.cteate_meeting.validators.data.CreateMeetingFieldItemType.*
import com.nowiwr01p.domain.cteate_meeting.validators.data.CustomTextFieldType.*
import com.nowiwr01p.domain.cteate_meeting.validators.data.DetailsFieldType
import com.nowiwr01p.domain.cteate_meeting.validators.data.DetailsFieldType.*
import com.nowiwr01p.domain.cteate_meeting.validators.data.DynamicDetailsItem.*
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.*
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingContract.State
import com.nowiwr01p.meetings.ui.create_meeting.data.CustomTextFieldData
import com.nowiwr01p.meetings.ui.create_meeting.data.CustomTextFieldData.*
import com.nowiwr01p.meetings.ui.main.Category
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateMeetingMainScreen(
    navigator: Navigator,
    viewModel: CreateMeetingVewModel = getViewModel()
) {
    val state = viewModel.viewState.value

    val bottomSheetParams = BottomSheetParams(
        topPadding = 216.dp,
        content = CategoriesBottomSheet(state) { viewModel.setEvent(Event.OnSelectedCategoryClick(it)) }
    )

    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
        override fun onAddDetailsItem(type: DetailsFieldType) {
            viewModel.setEvent(Event.OnAddDetailsItemClick(type))
        }
        override fun onRemoveDetailsItem(type: DetailsFieldType, index: Int) {
            viewModel.setEvent(Event.OnRemoveDetailsItemClick(type, index))
        }
        override fun onEditDetailsItem(type: DetailsFieldType, index: Int, value: String) {
            viewModel.setEvent(Event.OnEditDetailsItemClick(type, index, value))
        }
        override fun onEditCustomTextField(type: CreateMeetingFieldItemType, value: String) {
            viewModel.setEvent(Event.OnEditCustomTextField(type, value))
        }
        override fun showCategoriesBottomSheet() {
            viewModel.setEvent(Event.ShowCategoriesBottomSheet(bottomSheetParams))
        }
        override fun navigateToPreview() {
            viewModel.setEvent(Event.NavigateToPreview)
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
        override fun onDateSelected(date: Long) {
            viewModel.setEvent(Event.SelectDate(date))
        }
        override fun onTimeSelected(time: Long) {
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
            is Effect.NavigateToPreview -> {
                navigator.meetingsNavigator.navigateToMeetingInfo(true, it.meeting)
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
    FieldsContainer(state, listener)
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
    listener: Listener?
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
        .animateContentSize()
) {
    item { TopImageItem(state, listener).toUiItem(state) }
    item { Categories(state, listener) }
    item { TitleItem(state, listener).toUiItem(state)}
    item { DescriptionItem(state, listener).toUiItem(state) }
    item { Date(state, listener) }
    item { OpenDate(state, listener) }
    item { TelegramItem(state, listener).toUiItem(state) }
    item { PosterMotivationItem(state, listener).toUiItem(state) }
    item { Posters(state, listener) }
    item { Goals(state, listener) }
    item { Slogans(state, listener) }
    item { Strategy(state, listener) }
    item { PreviewButton(state, listener) }
}

/**
 * MODIFY [CustomTextFieldData] into [CustomTextField]
 */
@Composable
private fun CustomTextFieldData.toUiItem(state: State) = CustomTextField(state, this)

/**
 * TEXT FIELD
 */
@Composable
private fun CustomTextField(state: State, item: CustomTextFieldData) = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    val wrongInput = state.validationErrors.find { item.type == it?.type } != null
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
        keyboardOptions = KeyboardOptions(
            keyboardType = item.keyboardType
        ),
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
                    color = if (wrongInput) {
                        MaterialTheme.colors.graphicsRed
                    } else {
                        MaterialTheme.colors.graphicsSecondary
                    }
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
 * CATEGORIES
 */
@Composable
private fun Categories(
    state: State,
    listener: Listener?
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    FakeTextField(
        state = state,
        type = CATEGORIES,
        onClick = {
            focusManager.clearFocus()
            keyboard?.hide()
            listener?.showCategoriesBottomSheet()
        }
    ) {
        if (state.selectedCategories.isEmpty()) {
            FakeTitle("Категории")
        } else {
            CategoriesList(state)
        }
    }
}

@Composable
private fun CategoriesList(state: State) = LazyRow(
    modifier = Modifier.fillMaxSize(),
    verticalAlignment = Alignment.CenterVertically
) {
    val selectedCategories = state.selectedCategories.toList().sortedBy { it.backgroundColor }

    item { Spacer(modifier = Modifier.width(16.dp)) }
    items(
        key = { it.name },
        items = selectedCategories
    ) {
        Category(
            category = it,
            modifier = Modifier.animateItemPlacement()
        )
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
    LazyColumn {
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
        DateMainItem(state, checked)
        AnimatedVisibility(visible = checked.value) {
            Column {
                DateTimeSubItem(state, listener)
                ChooseStartLocationItem(state, listener)
                LocationItem(state, listener).toUiItem(state)
                LocationDetailsItem(state, listener).toUiItem(state)
                DrawPathItem(state, listener)
            }
        }
    }
}

@Composable
private fun DateMainItem(state: State, checked: MutableState<Boolean>) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    FakeTextField(
        state = state,
        type = DATE,
        content = { FakeTitle("Дата и локация") },
        onClick = {
            focusManager.clearFocus()
            keyboard?.hide()
            checked.value = !checked.value
        },
    )
}

@Composable
private fun DateTimeSubItem(state: State, listener: Listener?) = FakeTextField(
    state = state,
    type = DATE,
    showSubItemSlash = true,
    onClick = { listener?.showDateTimePicker() }
) {
    FakeTitle(
        hint = "Дата и время",
        text = if (state.selectedDate == 0L) "" else state.selectedDate.getFromPicker()
    )
}

@Composable
private fun ChooseStartLocationItem(state: State, listener: Listener?) = FakeTextField(
    state = state,
    type = LOCATION_COORDINATES,
    showSubItemSlash = true,
    onClick = { listener?.navigateChooseStartLocation() }
) {
    val text = if (state.startLocation.latitude == .0) "" else "Место встречи сохранено"
    FakeTitle(
        hint = "Место встречи",
        text = text
    )
}

@Composable
private fun DrawPathItem(state: State, listener: Listener?) = FakeTextField(
    state = state,
    type = PATH,
    showSubItemSlash = true,
    onClick = { listener?.navigateToMapDrawPath() }
) {
    val text = if (state.path.isEmpty()) "" else "Путь сохранён"
    FakeTitle(
        hint = "Путь",
        text = text
    )
}

/**
 * OPEN DATE
 */
@Composable
private fun OpenDate(state: State, listener: Listener?) = Column {
    val checked = rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    ExpandableItem("Открытая дата") {
        CheckBox(checked.value) {
            focusManager.clearFocus()
            keyboard?.hide()
            checked.value = !checked.value
        }
    }
    AnimatedVisibility(visible = checked.value) {
        OpenDateItem(state, listener).toUiItem(state)
    }
}

/**
 * POSTERS
 */
@Composable
private fun Posters(state: State, listener: Listener?) = ExpandableItems(
    state = state,
    type = POSTER_LINKS,
    title = "Ссылки на плакаты",
    items = state.posters,
    listener = listener
)

/**
 * GOALS
 */
@Composable
private fun Goals(state: State, listener: Listener?) = ExpandableItems(
    state = state,
    type = GOALS,
    title = "Цели",
    items = state.goals,
    listener = listener
)

/**
 * SLOGANS
 */
@Composable
private fun Slogans(state: State, listener: Listener?) = ExpandableItems(
    state = state,
    type = SLOGANS,
    title = "Лозунги",
    items = state.slogans,
    listener = listener
)

/**
 * STRATEGY
 */
@Composable
private fun Strategy(state: State, listener: Listener?) = ExpandableItems(
    state = state,
    type = STRATEGY,
    title = "Стратегия",
    items = state.strategy,
    listener = listener
)

/**
 * PREVIEW BUTTON
 */
@Composable
private fun PreviewButton(state: State, listener: Listener?) = StateButton(
    text = "Превью",
    onSendRequest = { listener?.navigateToPreview() },
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
        .clip(RoundedCornerShape(24.dp))
)

/**
 * FAKE TEXT FIELD
 */
@Composable
private fun FakeTextField(
    state: State,
    type: CreateMeetingFieldItemType,
    showSubItemSlash: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    val wrongInput = state.validationErrors.find { type == it?.type } != null
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
                    color = if (wrongInput) {
                        MaterialTheme.colors.graphicsRed
                    } else {
                        MaterialTheme.colors.graphicsSecondary
                    }
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
    ) {
        content()
    }
}

@Composable
private fun FakeTitle(hint: String, text: String = "") = Text(
    text = text.ifEmpty { hint },
    color = if (text.isEmpty()) Color(0x99000000) else Color.Black,
    style = MaterialTheme.typography.subHeadlineRegular,
    modifier = Modifier.padding(start = 16.dp)
)

/**
 * EXPANDABLE ITEMS
 */
@Composable
private fun ExpandableItems(
    state: State,
    type: DetailsFieldType,
    title: String,
    items: List<String>,
    listener: Listener?
) {
    val isVisible = rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    Column {
        ExpandableItem(title) {
            ClickableIcon(icon = Icons.Default.AddCircleOutline) {
                focusManager.clearFocus()
                keyboard?.hide()
                isVisible.value = true
                listener?.onAddDetailsItem(type)
            }
        }
        AnimatedVisibility(visible = isVisible.value) {
            Column(
                modifier = Modifier.animateContentSize()
            ) {
                items.forEachIndexed { index, item ->
                    val customTextFieldItem = CustomTextFieldData(
                        type = DYNAMIC,
                        value = item,
                        hint = "$index",
                        showSubItemSlash = true,
                        onValueChanged = { listener?.onEditDetailsItem(type, index, it) },
                        trailingIconCallback = {
                            listener?.onRemoveDetailsItem(type, index)
                            if (items.isEmpty()) isVisible.value = false
                        }
                    )
                    CustomTextField(state, customTextFieldItem)
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
        FakeTitle(hint)
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