package com.nowiwr01p.data.news.create_article.repository

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core.model.ArticleDB
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.news.create_article.repository.CreateArticleRepository
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.news.article.data.CreateArticleMode
import com.nowiwr01p.domain.news.article.data.CreateArticleMode.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CreateArticleRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): CreateArticleRepository {

    override suspend fun createArticle(mode: CreateArticleMode, article: Article) {
        when (mode) {
            SEND_TO_REVIEW -> sendArticleReview(article)
            PUBLISH_REVIEWED -> publishReviewed(article)
        }
    }

    /**
     * SEND ARTICLE TO REVIEW
     */
    private suspend fun sendArticleReview(article: Article) = withContext(dispatchers.io) {
        val sendArticle = async {
            references.getArticlePreviewReference(article.id)
                .setValue(ArticleDB.toArticleDB(article))
                .await()
        }
        val sendViewers = async {
            references.getNewsViewersReference()
                .child(article.id)
                .setValue(article.dateViewers)
                .await()
        }
        listOf(sendArticle, sendViewers)
    }

    /**
     * SEND ARTICLE TO REVIEW
     */
    private suspend fun publishReviewed(article: Article) = withContext(dispatchers.io) {
        val sendArticle = async {
            references.getArticleReference(article.id)
                .setValue(ArticleDB.toArticleDB(article))
                .await()
        }
        val removeFromPreview = async {
            references.getArticlePreviewReference(article.id)
                .removeValue()
                .await()
        }
        listOf(sendArticle, removeFromPreview).awaitAll()
    }
}