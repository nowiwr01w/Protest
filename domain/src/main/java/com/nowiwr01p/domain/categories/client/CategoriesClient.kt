package com.nowiwr01p.domain.categories.client

import com.nowiwr01p.core.model.Category
import kotlinx.coroutines.flow.StateFlow

interface CategoriesClient {
    suspend fun subscribeCategories()
    suspend fun getCategories(): StateFlow<List<Category>>
}