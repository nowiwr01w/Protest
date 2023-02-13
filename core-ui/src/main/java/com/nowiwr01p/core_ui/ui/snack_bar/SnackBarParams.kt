package com.nowiwr01p.core_ui.ui.snack_bar

import androidx.compose.ui.graphics.Color
import com.nowiwr01p.core_ui.theme.lightGraphicsGreen
import com.nowiwr01p.core_ui.theme.lightGraphicsRed

data class SnackBarParams(
    val text: String,
    val error: Boolean = true,
    val fromStatusBarColor: Color = Color.White,
    val endCallback: () -> Unit = {}
) {
    val toStatusBarColor: Color = if (error) lightGraphicsRed else lightGraphicsGreen
}