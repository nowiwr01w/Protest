package com.nowiwr01p.profile.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.ClickableIcon
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.alert_dialog.CustomAlertDialog
import com.nowiwr01p.core_ui.ui.animation.pressedAnimation
import com.nowiwr01p.profile.BuildConfig.*
import com.nowiwr01p.profile.R
import com.nowiwr01p.profile.ui.ProfileContract.*
import com.nowiwr01p.profile.ui.data.ProfileItem
import com.nowiwr01p.profile.ui.data.getProfileItems
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel


@Composable
fun ProfileMainScreen(
    editMode: Boolean,
    navigator: Navigator,
    viewModel: ProfileViewModel = getViewModel()
) {
    val state = viewModel.viewState.value
    val context = LocalContext.current

    val listener = object : Listener {
        override fun onEditClick() {
            viewModel.setEvent(Event.OnEditClick)
        }
        override fun onSaveClick() {
            viewModel.setEvent(Event.OnSaveClick)
        }
        override fun onCancelClick() {
            viewModel.setEvent(Event.OnCancelClick)
        }
        override fun onUserNameChanged(name: String) {
            viewModel.setEvent(Event.OnUserNameChanged(name))
        }
        override fun setAvatarPreview(uri: Uri) {
            viewModel.setEvent(Event.SetAvatarPreview(uri))   
        }
        override fun setStorageAvailable() {
            viewModel.setEvent(Event.SetStorageAvailable)
        }
        override fun requestPermission() {
            viewModel.setEvent(Event.RequestPermission)
        }
        override fun showPermissionAlert(show: Boolean) {
            viewModel.setEvent(Event.ShowPermissionAlert(show))
        }
        override fun redirectToSettings() {
            viewModel.setEvent(Event.RedirectToSettings)
        }
        override fun requestPermissionAlert() {
            viewModel.setEvent(Event.RequestPermissionAlert)
        }
        override fun logout() {
            showLogoutAlert(false)
            viewModel.setEvent(Event.Logout)
        }
        override fun toChangeCity() {
            navigator.authNavigator.toChooseCity(true)
        }
        override fun deleteAccount() {
            showDeleteAccountAlert(false)
            viewModel.setEvent(Event.DeleteAccount)
        }

        override fun showLogoutAlert(show: Boolean) {
            viewModel.setEvent(Event.ShowLogoutAlert(show))
        }

        override fun showDeleteAccountAlert(show: Boolean) {
            viewModel.setEvent(Event.ShowDeleteAccountAlert(show))
        }

        override fun openLink(link: String) {
            viewModel.setEvent(Event.OpenLink(link))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init(editMode))
    }

    val launcher = rememberLauncherForActivityResult(RequestPermission()) { granted ->
        when {
            granted -> listener.setStorageAvailable()
            state.shouldRequestPermission && state.shouldRequestAlert -> listener.showPermissionAlert(true)
        }
    }
    SideEffect {
        if (state.shouldRequestPermission) launcher.launch(READ_EXTERNAL_STORAGE)
    }

    val gallery = rememberLauncherForActivityResult(GetContent()) { pickedPhotoUri ->
        if (pickedPhotoUri != null) listener.setAvatarPreview(pickedPhotoUri)
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.ChoosePhoto -> {
                gallery.launch("image/*")
            }
            is Effect.RedirectToSettings -> {
                val settingsIntent = Intent(
                    ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + context.packageName)
                )
                context.startActivity(settingsIntent)
            }
            is Effect.NavigateToAuth -> {
                navigator.authNavigator.toAuth()
            }
        }
    }

    if (state.showPermissionAlert) {
        PermissionAlert(listener)
    }
    if (state.showLogoutAlert) {
        LogoutConfirmAlert(listener)
    }
    if (state.showDeleteAccountAlert) {
        DeleteAccountConfirmAlert(listener)
    }
    ProfileMainScreenContent(
        state = state,
        listener = listener
    )
}

@Composable
private fun ProfileMainScreenContent(
    state: State,
    listener: Listener?
) {
    val items = getProfileItems(state, listener)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundSecondary)
    ) {
        item { TopContainer(state, listener) }
        items(items) {
            ItemsContainer(it)
        }
        item { AppVersion() }
    }
}

/**
 * ROUNDED CONTAINER
 */
@Composable
private fun TopContainer(state: State, listener: Listener?) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        .background(Color.White)
        .padding(top = 16.dp)
        .animateContentSize()
) {
    val (avatar, name, role, edit, chat, space) = createRefs()

    val avatarModifier = Modifier
        .constrainAs(avatar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    if (state.editMode) {
        AvatarStub(state, listener, avatarModifier)
    } else {
        Avatar(state, avatarModifier)
    }

    val editModifier = Modifier
        .constrainAs(edit) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    if (state.editProgress) {
        CircularProgressIndicator(
            strokeWidth = 2.dp,
            color = MaterialTheme.colors.mainBackgroundColor,
            modifier = editModifier
                .padding(top = 6.dp, end = 16.dp)
                .size(20.dp)
        )
    } else {
        ClickableIcon(
            modifier = editModifier.padding(end = 10.dp),
            icon = if (state.editMode) R.drawable.ic_save else R.drawable.ic_edit,
            onClick = {
                if (state.editMode) listener?.onSaveClick() else listener?.onEditClick()
            }
        )
    }

    val chatModifier = Modifier
        .padding(start = 10.dp)
        .constrainAs(chat) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
    ClickableIcon(
        modifier = chatModifier,
        icon = if (state.editMode) R.drawable.ic_cancel else R.drawable.ic_chat,
        onClick = {
            if (state.editMode) listener?.onCancelClick() else listener?.openLink(TELEGRAM_LINK)
        },
    )

    val nameModifier = Modifier
        .padding(top = 8.dp, bottom = 4.dp, start = 24.dp, end = 24.dp)
        .constrainAs(name) {
            start.linkTo(avatar.start)
            end.linkTo(avatar.end)
            top.linkTo(avatar.bottom)
        }
    if (state.editMode) {
        EditNameTextField(
            state = state,
            listener = listener,
            modifier = nameModifier
        )
    } else {
        Text(
            text = state.user.name.ifEmpty { "Profile" },
            style = MaterialTheme.typography.title1Bold,
            color = MaterialTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = nameModifier
        )
    }

    val roleModifier = Modifier
        .constrainAs(role) {
            start.linkTo(name.start)
            end.linkTo(name.end)
            top.linkTo(name.bottom)
        }
    if (!state.editMode) {
        var userRole = when {
            state.user.tempOrganizer && state.user.tempWriter -> "Временный организатор, редактор новостей"
            state.user.tempOrganizer -> "Временный организатор"
            state.user.tempWriter -> "Временный редактор"
            else -> "Для отображения роли получите доступ"
        }
        userRole = when {
            state.user.organizer && state.user.writer -> "Организатор, редактор новостей"
            state.user.organizer -> "Организатор"
            state.user.writer -> "Редактор новостей"
            else -> userRole
        }
        Text(
            text = userRole,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.textColorSecondary,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = roleModifier
        )
    }

    val barrier = createBottomBarrier(name, role)
    val spaceModifier = Modifier
        .height(12.dp)
        .constrainAs(space) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(barrier)
        }
    Spacer(modifier = spaceModifier)
}

/**
 * AVATAR
 */
@Composable
private fun Avatar(state: State, modifier: Modifier) = Box(
    modifier = modifier
        .size(132.dp)
        .clip(CircleShape)
        .border(2.dp, Color(0xFFFC4C4C), CircleShape),
    contentAlignment = Alignment.Center
) {
    Box(
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val url = state.user.avatar.ifEmpty { "https://avatars.githubusercontent.com/u/39140585?v=4" }
        CoilImage(
            imageModel = { url },
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}

@Composable
private fun AvatarStub(
    state: State,
    listener: Listener?, 
    modifier: Modifier
) {
    val callback = {
        when {
            state.isStorageAvailable -> listener?.setStorageAvailable()
            !state.shouldRequestPermission && !state.shouldRequestAlert -> listener?.requestPermission()
            else -> listener?.requestPermissionAlert()
        }
    }
    Box(
        modifier = modifier
            .pressedAnimation { callback.invoke() }
            .size(132.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.backgroundSecondary),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.previewEditAvatar.isEmpty() -> CoilImage(
                modifier = Modifier.size(42.dp),
                imageModel = { R.drawable.ic_plus },
                imageOptions = ImageOptions(
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.textColorSecondary)
                )
            )
            else -> Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = state.previewEditAvatar.toUri())
                        .build()
                ),
                contentDescription = "Preview avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(132.dp)
            )
        }
    }
}

/**
 * ITEMS CONTAINER
 */
@Composable
private fun ItemsContainer(container: Pair<String, List<ProfileItem>>) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
) {
    Category(container.first)

    container.second.forEach { item ->
        InfoItem(
            name = item.name,
            startIcon = item.startIcon,
            endIcon = item.endIcon,
            action = item.onClick,
        )
    }
}

/**
 * CATEGORY
 */
@Composable
private fun Category(name: String) = Text(
    text = name,
    style = MaterialTheme.typography.title4Bold,
    color = MaterialTheme.colors.textPrimary,
    textAlign = TextAlign.Start,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
)

/**
 * INFO ITEM
 */
@Composable
private fun InfoItem(
    name: String,
    startIcon: Int,
    endIcon: Int? = null,
    action: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { action.invoke() }
    ) {
        Icon(
            painter = painterResource(startIcon),
            contentDescription = "Info item icon",
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                .size(22.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.calloutRegular,
            color = MaterialTheme.colors.textPrimary,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(start = 16.dp)
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        val size = if (endIcon == null) 24.dp else 20.dp
        Icon(
            painter = if (endIcon == null) {
                rememberVectorPainter(image = Icons.Default.KeyboardArrowRight)
            } else {
                painterResource(id = endIcon)
            },
            contentDescription = "Info item icon",
            tint = if (endIcon == null) Color.Black else MaterialTheme.colors.graphicsGreen,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(size)
        )
    }
}

/**
 * EDIT NAME TEXT FIELD
 */
@Composable
private fun EditNameTextField(
    state: State,
    listener: Listener?,
    modifier: Modifier
) {
    TextField(
        value = state.previewEditName,
        onValueChange = {
            listener?.onUserNameChanged(it)
        },
        placeholder = {
            Text(text = "Введите имя")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        ),
        trailingIcon = {
            Text(
                text = "${state.previewEditName.length}/24",
                modifier = Modifier.padding(end = 8.dp),
                color = MaterialTheme.colors.textColorSecondary,
                style = MaterialTheme.typography.subHeadlineRegular
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 4.dp, start = 16.dp, end = 16.dp)
            .border(
                border = BorderStroke(
                    width = 1.25.dp,
                    color = MaterialTheme.colors.graphicsSecondary
                ),
                shape = RoundedCornerShape(12.dp)
            )
    )
}

/**
 * APP VERSION
 */
@Composable
private fun AppVersion() = Text(
    text = "App Version: 1.0",
    style = MaterialTheme.typography.footnoteRegular,
    color = MaterialTheme.colors.textPrimary,
    textAlign = TextAlign.Center,
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
)

/**
 * ALERT DIALOG
 */
@Composable
private fun PermissionAlert(listener: Listener) = CustomAlertDialog(
    title = "Необходимо разрешение",
    description = "В прошлый раз вы не дали доступ к фотографиям. Теперь нужно дать разрешение " +
            "в настройках приложения.\n" +
            "Перейти в настройки?",
    negativeCallback = { listener.showPermissionAlert(false) },
    positiveCallback = { listener.redirectToSettings() }
)

/**
 * LOGOUT CONFIRMATION ALERT
 */
@Composable
private fun LogoutConfirmAlert(listener: Listener) = CustomAlertDialog(
    title = "Выйти",
    description = "Возвращайтесь скорее. России нужны люди, готовые бороться.",
    negativeCallback = { listener.showLogoutAlert(false) },
    positiveCallback = { listener.logout() }
)

/**
 * DELETE ACCOUNT CONFIRMATION ALERT
 */
@Composable
private fun DeleteAccountConfirmAlert(listener: Listener) = CustomAlertDialog(
    title = "Удалить аккаунт",
    description = "Уверены, что не хотите участвовать в создании прекрасной России будущего?",
    negativeCallback = { listener.showDeleteAccountAlert(false) },
    positiveCallback = { listener.deleteAccount() }
)

/**
 * PREVIEW
 */
@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    ProfileMainScreenContent(
        state = State(),
        listener = null
    )
}