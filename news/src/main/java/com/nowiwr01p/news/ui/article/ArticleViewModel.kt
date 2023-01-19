package com.nowiwr01p.news.ui.article

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.ui.button.ButtonState.*
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.article.SetArticleViewedUseCase
import com.nowiwr01p.domain.create_article.CreateArticleUseCase
import com.nowiwr01p.news.ui.article.ArticleContract.*
import kotlinx.coroutines.delay
import timber.log.Timber

class ArticleViewModel(
    private val setArticleViewedUseCase: SetArticleViewedUseCase,
    private val createArticleUseCase: CreateArticleUseCase
) : BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.article)
            is Event.PublishArticle -> publish()
        }
    }

    private fun init(article: Article) {
        setState { copy(article = article) }
        setArticleViewed(article.id)
    }

    private fun setArticleViewed(id: String) = io {
        runCatching {
            setArticleViewedUseCase.execute(id)
        }.onSuccess {
            setState { copy(viewsCount = it) }
        }
    }

    private fun publish() = io {
        setState { copy(publishButtonState = SEND_REQUEST) }
        runCatching {
            createArticleUseCase.execute(viewState.value.article)
        }.onSuccess {
            setState { copy(publishButtonState = SUCCESS) }
        }.onFailure {
            setState { copy(publishButtonState = ERROR) }
            delay(3000)
            setState { copy(publishButtonState = DEFAULT) }
        }
    }
}