package com.nowiwr01p.news.ui.news

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.config.RemoteConfig
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.news.main.usecase.GetNewsScreenCacheUseCase
import com.nowiwr01p.domain.news.main.usecase.GetNewsUseCase
import com.nowiwr01p.domain.news.main.usecase.SaveNewsScreenCacheUseCase
import com.nowiwr01p.domain.news.main.usecase.data.NewsScreenCacheData
import com.nowiwr01p.domain.user.usecase.GetUserUseCase
import com.nowiwr01p.news.ui.news.NewsContract.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val config: RemoteConfig,
    private val getNews: GetNewsUseCase,
    private val getNewsScreenCache: GetNewsScreenCacheUseCase,
    private val saveNewsScreenCache: SaveNewsScreenCacheUseCase,
    private val getLocalUserUseCase: GetUserUseCase
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init()
            is Event.OnArticleClick -> setEffect { Effect.ShowArticle(event.article) }
            is Event.NavigateToCreateArticle -> setEffect { Effect.NavigateToCreateArticle }
        }
    }

    private fun init() = io {
        setState { copy(isLoading = true) }
        runCatching {
            getScreenCache()
            getUserData()
            checkEverybodyCanWriteNews()
            getNews()
        }.onSuccess {
            saveScreenCache()
            setState { copy(isLoading = false) }
        }
    }

    /**
     * USER
     */
    private suspend fun getUserData() = launch {
        getLocalUserUseCase.execute().collect {  user ->
            setState { copy(user = user) }
        }
    }

    /**
     * NEWS
     */
    private suspend fun getNews() = launch {
        getNews.execute().collect { news ->
            setState { copy(newsList = news) }
        }
    }

    private fun checkEverybodyCanWriteNews() = config
        .isWriteNewsEverybodyActivated()
        .let {
            setState { copy(everybodyCanWriteNews = it.value) }
        }

    /**
     * SCREEN CACHE
     */
    private suspend fun getScreenCache() = getNewsScreenCache.execute().let { cache ->
        if (cache.data.news.isNotEmpty()) {
            setState { copy(newsList = cache.data.news, isLoading = false) }
        }
    }

    private suspend fun saveScreenCache() = with(viewState.value) {
        saveNewsScreenCache.execute(NewsScreenCacheData(news = newsList))
    }
}