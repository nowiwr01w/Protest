package com.nowiwr01p.news.ui.news

import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.news.usecase.GetNewsScreenCacheUseCase
import com.nowiwr01p.domain.news.usecase.GetNewsUseCase
import com.nowiwr01p.domain.news.usecase.SaveNewsScreenCacheUseCase
import com.nowiwr01p.domain.news.usecase.data.NewsScreenCacheData
import com.nowiwr01p.news.ui.news.NewsContract.*

class NewsViewModel(
    private val getNews: GetNewsUseCase,
    private val getNewsScreenCache: GetNewsScreenCacheUseCase,
    private val saveNewsScreenCache: SaveNewsScreenCacheUseCase
) : BaseViewModel<Event, State, Effect>() {

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
//        runCatching {
            getScreenCache()
            getNews()
//        }.onSuccess {
            saveScreenCache()
            setState { copy(isLoading = false) }
//        }
    }

    /**
     * NEWS
     */
    private suspend fun getNews() = getNews.execute().let { news ->
        setState { copy(newsList = news) }
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