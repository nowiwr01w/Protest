package com.nowiwr01p.meetings.ui.meeting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val (first, second, third, fourth, fifth) = createRefs()

    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(first) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 32.dp)
            }
    )
    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(second) {
                start.linkTo(parent.start, 64.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }
    )
    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(fourth) {
                top.linkTo(parent.top, 16.dp)
                end.linkTo(parent.end, 32.dp)
            }
    )
    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(fifth) {
                end.linkTo(parent.end, 64.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }
    )
    MapUnknownPlaceItem(
        modifier = Modifier
            .constrainAs(third) {
                start.linkTo(second.end)
                end.linkTo(fifth.start)
                top.linkTo(fourth.top)
                bottom.linkTo(fifth.bottom)
            }
    )
}

@Preview(showBackground = true)
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
            color = MaterialTheme.colors.backgroundSecondary,
            shape = CircleShape
        )
) {
    Text(
        text = "\uD83E\uDD14",
        style = TextStyle.Default.copy(fontSize = 20.sp)
    )
}