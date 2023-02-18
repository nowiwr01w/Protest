package com.nowiwr01p.data.meetings.main

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.datastore.cities.data.Reaction
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MeetingsRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): MeetingsRepository {

    /**
     * MEETINGS
     */
    private val meetingsFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(listOf())

    override suspend fun getMeetings() = meetingsFlow

    override suspend fun subscribeMeetings() = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, Meeting>> { map ->
            val updated = map.values.sortedByDescending { meeting -> meeting.date }
            meetingsFlow.emit(updated)
        }
        val reference = references.getMeetingsReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        ReferencedListener(reference, listener)
    }

    /**
     * UNPUBLISHED MEETINGS
     */
    override suspend fun getUnpublishedMeetings() = withContext(dispatchers.io) {
        references.getUnpublishedMeetingsReference().get().await()
            .children
            .map { snapshot -> snapshot.getValue<Meeting>()!! }
    }

    /**
     * REACTIONS
     */
    private val reactionsFlow: MutableStateFlow<Map<String, Reaction>> = MutableStateFlow(mapOf())

    override suspend fun getReactions() = reactionsFlow

    override suspend fun subscribeReactions() = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, Reaction>> { meetingIdToReaction ->
            reactionsFlow.emit(meetingIdToReaction)
        }
        val reference = references.getMeetingReactionReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        ReferencedListener(reference, listener)
    }
}