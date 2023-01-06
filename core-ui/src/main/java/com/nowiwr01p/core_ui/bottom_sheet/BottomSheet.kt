package com.nowiwr01p.core_ui.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core_ui.theme.graphicsNeutral

@Composable
fun BottomSheet(
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DraggableTop()
        content()
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun DraggableTop() = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier
        .fillMaxWidth()
        .height(12.dp)
) {
    Box(
        modifier = Modifier
            .width(36.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.graphicsNeutral)
    )
}