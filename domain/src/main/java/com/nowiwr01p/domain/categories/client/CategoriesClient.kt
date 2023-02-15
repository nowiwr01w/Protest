package com.nowiwr01p.domain.categories.client

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.app.EventListener
import kotlinx.coroutines.flow.StateFlow

interface CategoriesClient {
    suspend fun subscribeCategories(): EventListener
    suspend fun getCategories(): StateFlow<List<Category>>
}