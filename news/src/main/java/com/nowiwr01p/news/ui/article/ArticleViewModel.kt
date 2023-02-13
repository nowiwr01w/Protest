package com.nowiwr01p.news.ui.article

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.ui.button.ButtonState.*
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.core_ui.ui.snack_bar.SnackBarParams
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.news.article.SetArticleViewedUseCase
import com.nowiwr01p.domain.news.article.data.CreateArticleMode
import com.nowiwr01p.domain.news.article.data.CreateArticleMode.*
import com.nowiwr01p.domain.news.create_article.usecase.CreateArticleUseCase
import com.nowiwr01p.news.ui.article.ArticleContract.*
import kotlinx.coroutines.delay

class ArticleViewModel(
    private val setArticleViewedUseCase: SetArticleViewedUseCase,
    private val createArticleUseCase: CreateArticleUseCase,
    private val showSnackBarHelper: ShowSnackBarHelper
) : BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.article)
            is Event.PublishArticle -> publish(event.mode)
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

    private fun publish(mode: CreateArticleMode) = io {
        setState { copy(publishButtonState = SEND_REQUEST) }
        runCatching {
            val args = CreateArticleUseCase.Args(mode, viewState.value.article)
            createArticleUseCase.execute(args)
        }.onSuccess {
            showSuccessSnackBar(mode)
            setState { copy(publishButtonState = SUCCESS) }
        }.onFailure {
            setState { copy(publishButtonState = ERROR) }
            delay(3000)
            setState { copy(publishButtonState = DEFAULT) }
        }
    }

    private fun showSuccessSnackBar(mode: CreateArticleMode) {
        val text = if (mode == SEND_TO_REVIEW) "Отправлено на ревью" else "Опубликовано"
        val params = SnackBarParams(
            text = text,
            endCallback = {
                setEffect { Effect.OnCreateArticleSuccess }
            }
        )
        showSnackBarHelper.showSuccessSnackBar(params)
    }
}