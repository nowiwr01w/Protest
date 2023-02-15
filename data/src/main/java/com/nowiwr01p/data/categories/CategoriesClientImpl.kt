package com.nowiwr01p.data.categories

import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.categories.client.CategoriesClient
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriesClientImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): CategoriesClient {

    private val categoriesFlow: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())

    override suspend fun getCategories() = categoriesFlow

    override suspend fun subscribeCategories() = withContext(dispatchers.io) {
        val listener = createEventListener<List<Category>> { categories ->
            val updated = categories.sortedBy { it.priority }
            CoroutineScope(dispatchers.io).launch {
                categoriesFlow.emit(updated)
            }
        }
        val reference = references.getCategoriesReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        EventListener(reference, listener)
    }
}