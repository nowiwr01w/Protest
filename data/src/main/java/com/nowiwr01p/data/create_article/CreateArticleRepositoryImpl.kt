package com.nowiwr01p.data.create_article

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.create_article.CreateArticleRepository
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CreateArticleRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): CreateArticleRepository {

    override suspend fun createArticle(article: Article): Unit = withContext(dispatchers.io) {
        references.getNewsReference()
            .child(article.title.text)
            .setValue(article)
            .await()
    }
}