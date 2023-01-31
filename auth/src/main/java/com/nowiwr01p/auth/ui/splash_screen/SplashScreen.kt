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
import com.nowiwr01p.auth.R
import com.nowiwr01p.auth.ui.splash_screen.SplashScreenContract.*
import com.nowiwr01p.auth.ui.splash_screen.data.ItemData
import com.nowiwr01p.auth.ui.splash_screen.data.getAnimatedTextItems
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.theme.subHeadlineRegular
import com.nowiwr01p.core_ui.theme.textColorSecondary
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    navigator: Navigator,
    viewModel: SplashScreenViewModel = getViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    SplashScreenContent(viewModel.viewState.value, navigator)
}

@Composable
private fun SplashScreenContent(state: State, navigator: Navigator) = Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.anim_splash_screen)
    )
    val logoAnimationState = animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition = composition,
        progress = { logoAnimationState.progress },
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
    )

    if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
        if (state.route.isNotEmpty()) {
            navigator.navigateToRoute(state.route)
        } else {
            navigator.authNavigator.toAuth()
        }
    }

    AnimatedText(logoAnimationState.progress,)
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