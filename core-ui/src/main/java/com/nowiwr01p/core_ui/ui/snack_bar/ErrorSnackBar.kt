package com.nowiwr01p.core_ui.ui.snack_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core_ui.theme.*

@Composable
fun ErrorSnackBar(text: String) {
    val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current)

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(top = statusBarHeight.dp)
            .background(MaterialTheme.colors.graphicsRed)
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}