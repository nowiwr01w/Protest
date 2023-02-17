package com.nowiwr01p.data.news.main

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.news.main.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
) : NewsRepository {

    private val newsFlow: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())

    /**
     * NEWS
     */
    override suspend fun getNews() = newsFlow

    override suspend fun subscribeNews() = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, Article>> { map ->
            val updated = map.values.sortedByDescending { article -> article.dateViewers.date }
            CoroutineScope(dispatchers.io).launch {
                newsFlow.emit(updated)
            }
        }
        val reference = references.getNewsReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        EventListener(reference, listener)
    }

    /**
     * UNPUBLISHED NEWS
     */
    override suspend fun getUnpublishedNews() = withContext(dispatchers.io) {
        references.getUnpublishedNewsReference().get().await()
            .children
            .map { snapshot -> snapshot.getValue<Article>()!! }
    }
}