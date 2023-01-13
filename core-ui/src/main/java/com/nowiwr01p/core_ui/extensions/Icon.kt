package com.nowiwr01p.core_ui.extensions

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .clip(RoundedCornerShape(14.dp))
        .clickable { onClick.invoke() }
) {
    Icon(
        painter = painterResource(icon),
        contentDescription = "Drawable clickable icon",
        modifier = Modifier.padding(6.dp)
    )
}

@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .clip(RoundedCornerShape(14.dp))
        .clickable { onClick.invoke() }
) {
    Icon(
        painter = rememberVectorPainter(icon),
        contentDescription = "Vector clickable icon",
        modifier = Modifier.padding(6.dp)
    )
}