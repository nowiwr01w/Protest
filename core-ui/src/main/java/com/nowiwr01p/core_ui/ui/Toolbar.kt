package com.nowiwr01p.core_ui.ui

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
import com.nowiwr01p.core_ui.theme.textPrimary

@Composable
fun ToolbarTop(
    title: @Composable RowScope.() -> Unit = {},
    backIcon: @Composable BoxScope.() -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier,
    showElevation: Boolean = false
) {
    Surface(elevation = if (showElevation) 4.dp else 0.dp) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
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
                    .padding(end = 16.dp)
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
fun ToolbarBackButton(onBack: () -> Unit) = Icon(
    painter = rememberVectorPainter(Icons.Default.ArrowBack),
    contentDescription = "",
    tint = Color.White,
    modifier = Modifier
        .padding(start = 12.dp)
        .clip(RoundedCornerShape(14.dp))
        .clickable { onBack() }
        .padding(4.dp)
)