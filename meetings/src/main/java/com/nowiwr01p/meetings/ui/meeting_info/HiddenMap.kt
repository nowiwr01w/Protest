package com.nowiwr01p.meetings.ui.meeting_info

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nowiwr01p.core_ui.theme.*

@Preview(showBackground = true)
@Composable
internal fun MapUnknownPlaceContainer() = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
) {
    val (text1, text2, text3, text4, text5) = createRefs()
    val (smile1, smile2, smile3, smile4, smile5) = createRefs()

    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(smile1) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 32.dp)
            }
    )
    MapUnknownTextItem(
        text = "А",
        modifier = Modifier
            .constrainAs(text1) {
                start.linkTo(smile1.start)
                end.linkTo(smile2.end)
                top.linkTo(smile1.bottom)
                bottom.linkTo(smile2.top)
            }
    )
    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(smile2) {
                start.linkTo(parent.start, 64.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }
    )
    MapUnknownTextItem(
        text = "Где",
        modifier = Modifier
            .rotate(-45f)
            .constrainAs(text2) {
                start.linkTo(smile2.start)
                end.linkTo(smile3.end)
                bottom.linkTo(smile3.top)
            }
    )
    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(smile4) {
                top.linkTo(parent.top, 16.dp)
                end.linkTo(parent.end, 32.dp)
            }
    )
    MapUnknownTextItem(
        text = "Же",
        modifier = Modifier
            .rotate(45f)
            .constrainAs(text3) {
                start.linkTo(smile3.end)
                end.linkTo(smile4.start)
                bottom.linkTo(smile3.top)
            }
    )
    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(smile5) {
                end.linkTo(parent.end, 64.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }
    )
    MapUnknownTextItem(
        text = "Будет",
        modifier = Modifier
            .constrainAs(text4) {
                start.linkTo(smile4.start)
                end.linkTo(smile4.end)
                top.linkTo(smile4.bottom)
                bottom.linkTo(smile5.top)
            }
    )
    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(smile3) {
                start.linkTo(smile2.end)
                end.linkTo(smile5.start)
                top.linkTo(smile4.top)
                bottom.linkTo(smile5.bottom)
            }
    )
    MapUnknownTextItem(
        text = "Митинг",
        modifier = Modifier
            .constrainAs(text5) {
                start.linkTo(smile2.end)
                end.linkTo(smile5.start)
                top.linkTo(smile3.bottom)
                bottom.linkTo(smile2.bottom)
            }
    )
}

@Composable
private fun MapUnknownPlaceItem(modifier: Modifier = Modifier) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
        .size(64.dp)
        .clip(CircleShape)
        .border(
            width = 2.dp,
            color = MaterialTheme.colors.graphicsBlue,
            shape = CircleShape
        )
        .background(
            color = Color.White,
            shape = CircleShape
        )
) {
    Text(
        text = "\uD83E\uDD14",
        style = TextStyle.Default.copy(fontSize = 20.sp)
    )
}

@Composable
private fun MapUnknownTextItem(
    text: String,
    modifier: Modifier = Modifier
) = Text(
    text = text,
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.headline,
    modifier = modifier.shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        ambientColor = Color.Black.copy(alpha = 0.45f),
        spotColor = Color.Black.copy(alpha = 0.45f),
    )
)