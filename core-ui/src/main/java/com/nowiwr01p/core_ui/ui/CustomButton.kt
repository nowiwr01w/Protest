package com.nowiwr01p.core_ui.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nowiwr01p.core_ui.R
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.ui.ButtonState.*

/**
 * BUTTON
 */
@Composable
fun StateButton(
    text: String,
    modifier: Modifier = Modifier,
    icon: Int? = null,
    state: ButtonState = DEFAULT,
    enabled: Boolean = true,
    onSendRequest: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {}
) {
    val successErrorColor by animateColorAsState(
        targetValue = when (state) {
            ERROR -> MaterialTheme.colors.graphicsRed
            SUCCESS -> MaterialTheme.colors.graphicsGreen
            else -> Color(0xFF3f4257)
        },
    )
    val backgroundColor = when (state) {
        INIT_LOADING -> MaterialTheme.colors.backgroundSecondary.copy(alpha = 0.9f)
        DEFAULT -> if (enabled) {
            Color(0xFF3f4257)
        } else {
            MaterialTheme.colors.backgroundSecondary.copy(alpha = 0.9f)
        }
        SEND_REQUEST -> Color(0xFF3f4257)
        SUCCESS, ERROR -> successErrorColor
    }

    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = backgroundColor
        ),
        onClick = {
            if (state == DEFAULT) onSendRequest.invoke()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        when (state) {
            DEFAULT -> DefaultText(text, icon, enabled, state)
            SUCCESS, ERROR -> AnimatedIcon(state, onSuccess, onError)
            INIT_LOADING, SEND_REQUEST -> Progress(state)
        }
    }
}

/**
 * BUTTON TEXT
 */
@Composable
private fun DefaultText(
    text: String,
    icon: Int?,
    enabled: Boolean,
    state: ButtonState
) = Row {
    if (icon != null) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "icon",
            modifier = Modifier.padding(end = 10.dp)
        )
    }
    Text(
        text = text,
        style = MaterialTheme.typography.button,
        color = when (state) {
            DEFAULT, INIT_LOADING -> if (enabled) Color.White else MaterialTheme.colors.textColorSecondary
            else -> MaterialTheme.colors.textColorSecondary
        }
    )
}

/**
 * CIRCULAR PROGRESS
 */
@Composable
private fun Progress(state: ButtonState) = CircularProgressIndicator(
    strokeWidth = 2.dp,
    modifier = Modifier.size(20.dp),
    color = if (state == INIT_LOADING) MaterialTheme.colors.graphicsSecondary else Color.White
)

/**
 * ANIMATE DRAWING ICON
 */
@Composable
private fun AnimatedIcon(
    state: ButtonState,
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {}
) {
    var callbackWasInvoked by remember { mutableStateOf(false) }
    val animationFile = if (state == ERROR) R.raw.anim_button_error else R.raw.anim_button_success
    val endCallback = if (state == ERROR) onError else onSuccess

    val composition = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animationFile)
    )
    val progress by animateLottieCompositionAsState(
        speed = 0.7f,
        iterations = 1,
        isPlaying = true,
        composition = composition.value
    )

    LottieAnimation(
        progress = { progress },
        composition = composition.value,
        modifier = Modifier.size(24.dp)
    )

    if (!callbackWasInvoked && progress == 1f) {
        endCallback.invoke()
        callbackWasInvoked = true
    }
}

/**
 * BUTTON STATE TYPES
 */
enum class ButtonState {
    INIT_LOADING,
    DEFAULT,
    SEND_REQUEST,
    SUCCESS,
    ERROR
}

/**
 * PREVIEWS
 */
@Preview(showBackground = true)
@Composable
private fun PreviewInit() {
    StateButton(
        text = "Confirm",
        state =  INIT_LOADING
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewDefault() {
    StateButton(
        text = "Confirm",
        state =  DEFAULT
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewDefaultDisabled() {
    StateButton(
        text = "Confirm",
        state =  DEFAULT,
        enabled = false
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSendRequest() {
    StateButton(
        text = "Confirm",
        state =  SEND_REQUEST
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSuccess() {
    StateButton(
        text = "Confirm",
        state =  SUCCESS
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewError() {
    StateButton(
        text = "Confirm",
        state =  ERROR
    )
}