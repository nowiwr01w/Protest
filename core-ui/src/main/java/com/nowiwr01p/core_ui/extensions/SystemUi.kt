package com.nowiwr01p.core_ui.extensions

import android.app.Activity
import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nowiwr01p.core_ui.theme.mainBackgroundColor

@Composable
fun setSystemUiColor(
    color: Color = MaterialTheme.colors.mainBackgroundColor
) {
    rememberSystemUiController().apply {
        setStatusBarColor(color)
    }
}

@Composable
fun Context.setStatusBarTransparent(toTransparent: Boolean) = SideEffect {
    with(this as Activity) {
        WindowCompat.setDecorFitsSystemWindows(window, !toTransparent)
        WindowCompat.getInsetsController(window, window.decorView.rootView).isAppearanceLightStatusBars = true
        window.statusBarColor = if (toTransparent) Color.Transparent.toArgb() else Color.White.toArgb()
    }
}