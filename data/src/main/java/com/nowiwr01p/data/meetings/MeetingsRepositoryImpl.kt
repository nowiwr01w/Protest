package com.nowiwr01p.data.meetings

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.datastore.location.data.Meeting
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.core.model.Category
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
            .map { snapshot -> snapshot.getValue<Category>()!! }
            .sortedBy { category -> category.priority }
    }

    override suspend fun getMeetings() = withContext(dispatchers.io) {
        references.getMeetingsReference().get().await()
            .children
            .map { snapshot -> snapshot.getValue<Meeting>()!! }
            .sortedByDescending { meeting -> meeting.date }
    }
}