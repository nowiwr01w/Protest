package com.nowiwr01p.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nowiwr01p.core_ui.extensions.ClickableIcon
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
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
private fun ProfileMainScreenContent(state: State) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.backgroundSecondary)
) {
    TopContainer()
    AccessContainer()
    AboutProjectContainer()
    PoliticsContainer()
    AppVersion()
}

/**
 * ROUNDED CONTAINER
 */
@Composable
private fun TopContainer() = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        .background(Color.White)
        .padding(top = 16.dp)
) {
    val (avatar, name, role, edit, chat) = createRefs()

    val avatarModifier = Modifier
        .size(132.dp)
        .clip(CircleShape)
        .constrainAs(avatar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    CoilImage(
        imageModel = { "https://avatars.githubusercontent.com/u/39140585?v=4" },
        modifier = avatarModifier
    )

    val editModifier = Modifier
        .padding(end = 16.dp)
        .constrainAs(edit) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }
    ClickableIcon(
        icon = Icons.Default.ModeEditOutline,
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
        icon = Icons.Default.ChatBubbleOutline,
        onClick = {},
        modifier = chatModifier
    )

    val nameModifier = Modifier
        .padding(top = 8.dp, start = 24.dp, end = 24.dp)
        .constrainAs(name) {
            start.linkTo(avatar.start)
            end.linkTo(avatar.end)
            top.linkTo(avatar.bottom)
        }
    Text(
        text = "Andrey Larionov",
        style = MaterialTheme.typography.title1Bold,
        color = MaterialTheme.colors.textPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = nameModifier
    )

    val roleModifier = Modifier
        .padding(top = 4.dp, bottom = 12.dp)
        .constrainAs(role) {
            start.linkTo(name.start)
            end.linkTo(name.end)
            top.linkTo(name.bottom)
        }
    Text(
        text = "Cоздатель новостей",
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.textColorSecondary,
        modifier = roleModifier
    )
}

/**
 * INCREASE ACCESS CONTAINER
 */
@Composable
private fun AccessContainer() = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
) {
    Category("Получить доступ")
    InfoItem("Стать организатором")
    InfoItem("Стать создателем новостей")
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
    InfoItem("Политика приватности")
    InfoItem("Политика обработки данных")
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
    InfoItem("Сообщить о баге")
    InfoItem("Присоединиться к разработке")
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
    action: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { action.invoke() }
    ) {
        Icon(
            painter = rememberVectorPainter(image = Icons.Default.AddCircleOutline),
            contentDescription = "Info item icon",
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
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
            painter = rememberVectorPainter(image = Icons.Default.KeyboardArrowRight),
            contentDescription = "Info item icon",
            modifier = Modifier.padding(horizontal = 16.dp)
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
        .padding(top = 16.dp)
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