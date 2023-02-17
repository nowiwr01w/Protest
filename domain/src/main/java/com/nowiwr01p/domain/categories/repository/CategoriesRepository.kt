package com.nowiwr01p.domain.categories.repository

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.app.ReferencedListener
import kotlinx.coroutines.flow.StateFlow

interface CategoriesRepository {
    suspend fun subscribeCategories(): ReferencedListener
    suspend fun getCategories(): StateFlow<List<Category>>
}