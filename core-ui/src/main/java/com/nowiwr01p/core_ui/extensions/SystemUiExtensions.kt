package com.nowiwr01p.core_ui.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nowiwr01p.core_ui.theme.mainBackgroundColor

@Composable
fun setSystemUiColor(
    color: Color = MaterialTheme.colors.mainBackgroundColor
) {
    rememberSystemUiController().apply {
        setStatusBarColor(color)
        setSystemBarsColor(color)
    }
}