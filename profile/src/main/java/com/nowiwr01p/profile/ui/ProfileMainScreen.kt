package com.nowiwr01p.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nowiwr01p.core_ui.extensions.ClickableIcon
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.profile.R
import com.nowiwr01p.profile.ui.ProfileContract.*
import com.skydoves.landscapist.coil.CoilImage
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileMainScreen(
    navigator: Navigator,
    viewModel: ProfileViewModel = getViewModel()
) {
    val listener = object : Listener {

    }

    ProfileMainScreenContent(viewModel.viewState.value)
}

@Composable
private fun ProfileMainScreenContent(state: State) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.backgroundSecondary)
) {
    item { TopContainer(state) }
    item { AccessContainer(state) }
    item { AboutProjectContainer() }
    item { PoliticsContainer() }
    item { AppVersion() }
}

/**
 * ROUNDED CONTAINER
 */
@Composable
private fun TopContainer(state: State) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        .background(Color.White)
        .padding(top = 16.dp)
) {
    val (avatar, name, role, edit, chat, space) = createRefs()

    val avatarModifier = Modifier
        .constrainAs(avatar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    Avatar(
        state = state,
        modifier = avatarModifier
    )

    val editModifier = Modifier
        .padding(end = 16.dp)
        .constrainAs(edit) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    ClickableIcon(
        icon = R.drawable.ic_edit,
        onClick = {},
        modifier = editModifier
    )

    val chatModifier = Modifier
        .padding(start = 16.dp)
        .constrainAs(chat) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        }
    ClickableIcon(
        icon = R.drawable.ic_chat,
        onClick = {},
        modifier = chatModifier
    )

    val nameModifier = Modifier
        .padding(top = 8.dp, bottom = 4.dp, start = 24.dp, end = 24.dp)
        .constrainAs(name) {
            start.linkTo(avatar.start)
            end.linkTo(avatar.end)
            top.linkTo(avatar.bottom)
        }
    Text(
        text = state.user.name.ifEmpty { "Andrey Larionov" },
        style = MaterialTheme.typography.title1Bold,
        color = MaterialTheme.colors.textPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = nameModifier
    )

    val roleModifier = Modifier
        .constrainAs(role) {
            start.linkTo(name.start)
            end.linkTo(name.end)
            top.linkTo(name.bottom)
        }
    if (state.user.organizer || state.user.writer) {
        val userRole = when {
            state.user.organizer && state.user.writer -> "Организатор, создатель новостей"
            state.user.organizer -> "Организатор"
            else -> "Создатель новостей"
        }
        Text(
            text = userRole,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.textColorSecondary,
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

/**
 * INCREASE ACCESS CONTAINER
 */
@Composable
private fun AccessContainer(state: State) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
) {
    Category("Получить доступ")
    InfoItem(
        name = "Стать организатором",
        startIcon = R.drawable.ic_brain,
        endIcon = if (state.user.organizer) R.drawable.ic_done else null
    )
    InfoItem(
        name = "Стать создателем новостей",
        startIcon = R.drawable.ic_lamp,
        endIcon = if (state.user.writer) R.drawable.ic_done else null
    )
}

/**
 * POLITICS CONTAINER
 */
@Composable
private fun PoliticsContainer() = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
) {
    Category("Условия пользования")
    InfoItem("Политика приватности", R.drawable.ic_security)
    InfoItem("Политика обработки данных", R.drawable.ic_data_management)
}

/**
 * ABOUT PROJECT
 */
@Composable
private fun AboutProjectContainer() = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
) {
    Category("О проекте")
    InfoItem("Сообщить о баге", R.drawable.ic_bug)
    InfoItem("Предложить свою идею", R.drawable.ic_suggest_idea)
    InfoItem("Присоединиться к разработке", R.drawable.ic_development)
    InfoItem("Поддержать проект", R.drawable.ic_money)
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
        Icon(
            contentDescription = "Info item icon",
            modifier = Modifier.padding(horizontal = 16.dp),
            tint = if (endIcon == null) Color.Black else MaterialTheme.colors.graphicsGreen,
            painter = if (endIcon == null) {
                rememberVectorPainter(image = Icons.Default.KeyboardArrowRight)
            } else {
                painterResource(id = endIcon)
            }
        )
    }
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
 * PREVIEW
 */
@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    ProfileMainScreenContent(
        state = State()
    )
}