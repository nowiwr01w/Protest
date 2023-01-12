package com.nowiwr01p.core_ui.extensions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun CheckBox(
    checked: Boolean = false,
    checkCallback: () -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(20.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .border(
                width = 1.25.dp,
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .toggleable(
                value = checked,
                role = Role.Checkbox,
                onValueChange = {
                    isChecked = !isChecked
                    checkCallback.invoke()
                }
            )
    ) {
        AnimatedVisibility(
            visible = checked,
            enter = slideInHorizontally(
                animationSpec = tween(200)
            ) + expandHorizontally(
                expandFrom = Alignment.Start,
                animationSpec = tween(200)
            ),
            exit = fadeOut()
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Check box",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}