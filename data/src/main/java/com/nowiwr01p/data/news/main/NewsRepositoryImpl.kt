package com.nowiwr01p.data.news.main

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.core.model.DateViewers
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.news.main.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
) : NewsRepository {

    /**
     * NEWS
     */
    private val newsFlow: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())

    override suspend fun getNews() = newsFlow

    override suspend fun subscribeNews() = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, Article>> { map ->
            val updated = map.values.sortedByDescending { article -> article.dateViewers.date }
            newsFlow.emit(updated)
        }
        val reference = references.getNewsReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        ReferencedListener(reference, listener)
    }

    /**
     * NEWS VIEWERS
     */
    private val newsViewersFlow: MutableStateFlow<Map<String, DateViewers>> = MutableStateFlow(mapOf())

    override suspend fun getNewsViewers() = newsViewersFlow

    override suspend fun subscribeNewsViewers() = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, DateViewers>> { map ->
            newsViewersFlow.emit(map)
        }
        val reference = references.getNewsViewersReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        ReferencedListener(reference, listener)
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