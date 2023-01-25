package com.nowiwr01p.data.news.main

import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.news.main.repository.NewsClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsClientImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
) : NewsClient {

    private val newsFlow: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())

    override suspend fun getNews() = newsFlow

    override suspend fun subscribeNews(): Unit = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, Article>> { map ->
            val updated = map.values.sortedByDescending { article -> article.dateViewers.date }
            CoroutineScope(dispatchers.io).launch {
                newsFlow.emit(updated)
            }
        }
        references.getNewsReference().addValueEventListener(listener)
    }
}