package com.nowiwr01p.core_ui.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import com.nowiwr01p.core_ui.theme.mainBackgroundColor

fun AnnotatedString.onTextClick(
    text: String,
    offset: Int,
    onClick: () -> Unit
) {
    getStringAnnotations(text, offset, offset).firstOrNull()?.let {
        onClick.invoke()
    }
}

@Composable
fun AnnotatedString.Builder.appendLink(name: String) {
    pushStringAnnotation(
        tag = name,
        annotation = name
    )
    withStyle(
        style = SpanStyle(color = MaterialTheme.colors.mainBackgroundColor)
    ) {
        append(name)
    }
    pop()
}