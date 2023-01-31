package com.nowiwr01p.data.news.create_article.repository

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.news.create_article.repository.CreateArticleRepository
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.news.article.data.CreateArticleMode
import com.nowiwr01p.domain.news.article.data.CreateArticleMode.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CreateArticleRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): CreateArticleRepository {

    override suspend fun createArticle(mode: CreateArticleMode, article: Article): Unit = withContext(dispatchers.io) {
        val reference = when (mode) {
            SEND_TO_REVIEW -> references.getArticlePreviewReference(article.id)
            PUBLISH_REVIEWED -> references.getArticleReference(article.id)
        }
        reference
            .setValue(article)
            .await()
            .also {
                if (mode == PUBLISH_REVIEWED) {
                    references.getArticlePreviewReference(article.id).removeValue().await()
                }
            }
    }
}