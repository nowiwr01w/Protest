package com.nowiwr01p.auth.ui.splash_screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nowiwr01p.auth.ui.splash_screen.SplashScreenContract.*
import com.nowiwr01p.auth.ui.splash_screen.data.ItemData
import com.nowiwr01p.auth.ui.splash_screen.data.getAnimatedTextItems
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.subHeadlineRegular
import com.nowiwr01p.core_ui.theme.textColorSecondary
import com.nowiwr01p.core_ui.ui.progress.StubProgressBar
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    navigator: Navigator,
    viewModel: SplashScreenViewModel = getViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    SplashScreenContent(
        state = viewModel.viewState.value,
        navigator = navigator
    )
}

@Composable
private fun SplashScreenContent(state: State, navigator: Navigator) = Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url(state.animationUrl)
    )
    val logoAnimationState = animateLottieCompositionAsState(composition)

    if (logoAnimationState.progress == 0f) {
        StubProgressBar()
    }
    LottieAnimation(
        composition = composition,
        progress = { logoAnimationState.progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .align(Alignment.Center)
    )

    if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
        if (state.route.isNotEmpty()) {
            navigator.navigateToRoute(state.route)
        } else {
            navigator.authNavigator.toAuth()
        }
    }

    AnimatedText(logoAnimationState.progress)
}

@Composable
private fun BoxScope.AnimatedText(progress: Float) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .align(Alignment.BottomCenter)
) {
    getAnimatedTextItems().forEach { item ->
        TextItem(item, progress)
    }
}

@Composable
private fun TextItem(item: ItemData, progress: Float) = AnimatedVisibility(
    visible = progress in (item.value..0.8f),
    enter = slideInVertically() + fadeIn(),
    exit = slideOutVertically() + fadeOut(),
    modifier = Modifier.padding(bottom = 4.dp),
) {
    Text(
        text = item.text,
        color = MaterialTheme.colors.textColorSecondary,
        style = MaterialTheme.typography.subHeadlineRegular
    )
}