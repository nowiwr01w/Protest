package com.nowiwr01p.news.ui.article

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.article.SetArticleViewedUseCase
import com.nowiwr01p.news.ui.article.ArticleContract.*

class ArticleViewModel(
    private val setArticleViewedUseCase: SetArticleViewedUseCase,
    private val openLinksHelper: OpenLinksHelper
) : BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.article)
            is Event.OpenLink -> openLink(event.link)
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

    private fun openLink(link: String) = io {
        openLinksHelper.openLink(link)
    }
}