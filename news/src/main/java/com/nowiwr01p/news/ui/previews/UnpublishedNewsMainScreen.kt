package com.nowiwr01p.news.ui.previews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.nowiwr01p.core_ui.navigators.main.Navigator
import com.nowiwr01p.core_ui.ui.progress.StubProgressBar
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarBackButton
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTitle
import com.nowiwr01p.core_ui.ui.toolbar.ToolbarTop
import com.nowiwr01p.news.ui.news.ArticleView
import com.nowiwr01p.news.ui.previews.UnpublishedNewsContract.*
import org.koin.androidx.compose.getViewModel

@Composable
fun UnpublishedNewsMainScreen(
    navigator: Navigator,
    viewModel: UnpublishedNewsViewModel = getViewModel()
) {
    val listener = object : Listener {
        override fun onBackClick() {
            navigator.navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Init)
    }

    UnpublishedNewsMainScreenContent(
        state = viewModel.viewState.value,
        listener = listener
    )
}

@Composable
private fun UnpublishedNewsMainScreenContent(state: State, listener: Listener) = Column(
    modifier = Modifier.fillMaxSize()
) {
    Toolbar(listener)
    if (state.loaded) {
        News(state)
    } else {
        StubProgressBar()
    }
}

/**
 * TOOLBAR
 */
@Composable
private fun Toolbar(listener: Listener) = ToolbarTop(
    title = {
        ToolbarTitle(title = "Готовы к публикации")
    },
    backIcon = {
        ToolbarBackButton {
            listener.onBackClick()
        }
    }
)

/**
 * NEWS
 */
@Composable
private fun News(state: State) = LazyColumn(
    modifier = Modifier.fillMaxSize()
) {
    items(state.news) { article ->
        ArticleView(article) {
            // TODO
        }
    }
}