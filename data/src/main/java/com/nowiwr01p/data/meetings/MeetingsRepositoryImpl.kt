package com.nowiwr01p.data.meetings

import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.data.Category
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MeetingsRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): MeetingsRepository {

    override suspend fun getCategories() = withContext(dispatchers.io) {
        references.getCategoriesReference().get().await()
            .children
            .map { snapshot -> snapshot.getValue(Category::class.java)!! }
            .sortedBy { category -> category.priority }
    }
}