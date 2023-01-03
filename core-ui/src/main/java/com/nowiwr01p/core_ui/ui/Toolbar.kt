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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core_ui.theme.calloutMedium

@Composable
fun ToolbarTop(
    title: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable BoxScope.() -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    search: @Composable BoxScope.() -> Unit = {},
    searchEnabled: Boolean = false,
    modifier: Modifier = Modifier,
    bankIcon: @Composable RowScope.() -> Unit = {},
) {
    Surface {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
        ) {
            if (searchEnabled) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box { navigationIcon() }
                    Box { search() }
                }
            } else {
                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    navigationIcon()
                }
                Row(modifier = Modifier.align(Alignment.Center)) {
                    bankIcon()
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
}

@Composable
fun ToolbarTitle(title: String) = Row(
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        text = title,
        style = MaterialTheme.typography.calloutMedium
    )
}

@Composable
fun ToolbarBackButton(onBack: () -> Unit) = Icon(
    painter = rememberVectorPainter(image = Icons.Default.ArrowBack),
    contentDescription = "",
    modifier = Modifier
        .padding(start = 12.dp)
        .clip(RoundedCornerShape(14.dp))
        .clickable { onBack() }
        .padding(4.dp)
)