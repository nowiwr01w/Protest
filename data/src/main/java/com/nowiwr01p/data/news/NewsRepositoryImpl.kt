package com.nowiwr01p.data.news

import android.util.Log
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.news.repository.NewsRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
) : NewsRepository {

    override suspend fun getNews(): List<Article> = withContext(dispatchers.io) {
        references.getNewsReference().get().await()
            .children
            .map {
                Log.d("Zhopa", "value = ${it.getValue(true)}")
                it.getValue(Article::class.java)!!
            }
    }
}