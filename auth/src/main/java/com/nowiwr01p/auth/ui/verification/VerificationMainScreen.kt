package com.nowiwr01p.auth.ui.verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nowiwr01p.auth.R
import com.nowiwr01p.auth.ui.verification.VerificationContract.*
import com.nowiwr01p.auth.ui.verification.data.Mode.SEND_AGAIN_TEXT
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.*
import com.nowiwr01p.core_ui.EffectObserver
import com.nowiwr01p.core_ui.extensions.setSystemUiColor
import com.nowiwr01p.core_ui.ui.button.StateButton
import org.koin.androidx.compose.getViewModel

@Composable
fun VerificationMainScreen(
    navigator: Navigator,
    viewModel: VerificationViewModel = getViewModel()
) {
    setSystemUiColor()

    val listener = object : Listener {
        override fun resendCode() {
            viewModel.setEvent(Event.ResendCode)
        }
        override fun onCheckCode() {
            viewModel.setEvent(Event.CheckIsEmailVerified)
        }
        override fun toLocations() {
            viewModel.setEvent(Event.NavigateToLocations)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    EffectObserver(viewModel.effect) {
        when (it) {
            is Effect.NavigateToLocations -> {
                navigator.authNavigator.toChooseCountry()
            }
        }
    }

    VerificationMainScreenContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun VerificationMainScreenContent(
    state: State,
    listener: Listener?
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.mainBackgroundColor)
) {
    Image(
        painter = painterResource(R.drawable.ic_login),
        contentDescription = "Verification icon",
        modifier = Modifier
            .padding(top = 32.dp)
            .size(96.dp)
            .clip(CircleShape)
    )
    VerificationContent(
        state = state,
        listener = listener,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White)
    )
}

@Composable
fun VerificationContent(
    state: State,
    listener: Listener?,
    modifier: Modifier
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Title()
    Subtitle1()
    Subtitle2()
    CheckButton(state, listener)
    TimerText(state, listener)
}

/**
 * TITLE
 */
@Composable
private fun Title() = Text(
    text = "Верификация",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.h1,
    modifier = Modifier.padding(top = 32.dp)
)

/**
 * DESCRIPTION
 */
@Composable
private fun Subtitle1() = Text(
    text = "Вам отправлена ссылка для подтверждения адреса электронной почты \n" +
            "Кликните на неё, а затем нажмите на этом экране кнопку для проверки",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
)

@Composable
private fun Subtitle2() = Text(
    text = "Не забудьте заглянуть в папки со спамом, рассылками и письмами социальных сетей",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
)

/**
 * VERIFICATION BUTTON
 */
@Composable
private fun ColumnScope.CheckButton(
    state: State,
    listener: Listener?
) {
    Spacer(modifier = Modifier.weight(1f))
    StateButton(
        text = "Проверить",
        state = state.buttonState,
        onSuccess = { listener?.toLocations() },
        onSendRequest = { listener?.onCheckCode() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .clip(RoundedCornerShape(24.dp))
    )
}

/**
 * TIMER OR SEND AGAIN TEXT OR PROGRESS
 */
@Composable
fun TimerText(
    state: State,
    listener: Listener?
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = 32.dp, bottom = 32.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(enabled = state.mode == SEND_AGAIN_TEXT) {
                listener?.resendCode()
            },
    ) {
        if (state.resendProgress) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                text = if (state.mode == SEND_AGAIN_TEXT) {
                    "Отправить письмо ещё раз"
                } else {
                    "Не пришёл код? Отправить снова через \n${state.timerSeconds} сек"
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subHeadlineRegular,
                color = MaterialTheme.colors.textColorSecondary,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }
    }
}

/**
 * PREVIEWS
 */
@Preview(showBackground = true)
@Composable
private fun Preview() = MeetingsTheme {
    VerificationMainScreenContent(
        state = State(),
        listener = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewResendCode() = MeetingsTheme {
    VerificationMainScreenContent(
        state = State(
            mode = SEND_AGAIN_TEXT
        ),
        listener = null
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewResendCodeProgress() = MeetingsTheme {
    VerificationMainScreenContent(
        state = State(
            mode = SEND_AGAIN_TEXT,
            resendProgress = true
        ),
        listener = null
    )
}