package com.nowiwr01p.data.news.article

import com.google.firebase.database.GenericTypeIndicator
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.news.article.ArticleRepository
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.user.client.UserClient
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ArticleRepositoryImpl(
    private val referencesRepository: FirebaseReferencesRepository,
    private val userClient: UserClient,
    private val dispatchers: AppDispatchers
): ArticleRepository {

    /**
     * ARTICLE VIEWS
     */
    override suspend fun setArticleViewed(articleId: String) = withContext(dispatchers.io) {
        val userId = userClient.getUserFlow().value.id
        val currentViewers = getArticleViews(articleId)
        val updatedViewers = currentViewers.toMutableList().apply {
            add(userId)
        }
        setArticleViews(articleId, updatedViewers)
        updatedViewers.size
    }

    private suspend fun getArticleViews(id: String) = referencesRepository
        .getArticleReference(id)
        .child(VIEWERS)
        .get()
        .await()
        .getValue(LIST_STRING_TOKEN)!!

    private suspend fun setArticleViews(id: String, viewers: List<String>) = referencesRepository
        .getArticleReference(id)
        .child(VIEWERS)
        .setValue(viewers)
        .await()

    private companion object {
        const val VIEWERS = "dateViewers/viewers"

        val LIST_STRING_TOKEN = object : GenericTypeIndicator<List<String>>() {}
    }
}