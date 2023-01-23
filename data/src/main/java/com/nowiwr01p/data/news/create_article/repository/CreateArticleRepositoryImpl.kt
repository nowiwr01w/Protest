package com.nowiwr01p.data.news.create_article.repository

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.news.create_article.repository.CreateArticleRepository
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CreateArticleRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): CreateArticleRepository {

    override suspend fun createArticle(article: Article): Unit = withContext(dispatchers.io) {
        references.getNewsReference()
            .child(article.id)
            .setValue(article)
            .await()
    }
}