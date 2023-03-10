package com.nowiwr01p.core_ui.ui.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core_ui.theme.calloutMedium
import com.nowiwr01p.core_ui.theme.mainBackgroundColor
import com.nowiwr01p.core_ui.theme.textPrimary

@Composable
fun ToolbarTop(
    title: @Composable RowScope.() -> Unit = {},
    backIcon: @Composable BoxScope.() -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier,
    showElevation: Boolean = false,
    blackColors: Boolean = false
) {
    val backgroundColor = when {
        blackColors -> MaterialTheme.colors.mainBackgroundColor
        else -> Color.White
    }
    Surface(
        modifier = modifier,
        elevation = if (showElevation) 4.dp else 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(backgroundColor)
                .height(56.dp)
        ) {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                backIcon()
            }
            Row(modifier = Modifier.align(Alignment.Center)) {
                title()
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                actions()
            }
        }
    }
}

@Composable
fun ToolbarTitle(
    title: String,
    textColor: Color = MaterialTheme.colors.textPrimary
) = Row(
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        text = title,
        color = textColor,
        style = MaterialTheme.typography.calloutMedium
    )
}

@Composable
fun ToolbarBackButton(
    blackColors: Boolean = false,
    onBack: () -> Unit
) {
    val iconColor = when {
        blackColors -> Color.White
        else -> Color.Black
    }
    Box(
        modifier = Modifier
            .padding(start = 6.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onBack() }
    ) {
        Icon(
            painter = rememberVectorPainter(Icons.Default.ArrowBack),
            contentDescription = "Toolbar back icon",
            tint = iconColor,
            modifier = Modifier.padding(6.dp)
        )
    }
}