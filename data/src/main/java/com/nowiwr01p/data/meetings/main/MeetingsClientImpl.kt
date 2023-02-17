package com.nowiwr01p.data.meetings.main

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.main.repository.MeetingsClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MeetingsClientImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): MeetingsClient {

    private val meetingsFlow: MutableStateFlow<List<Meeting>> = MutableStateFlow(listOf())

    /**
     * MEETINGS
     */
    override suspend fun getMeetings() = meetingsFlow

    override suspend fun subscribeMeetings() = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, Meeting>> { map ->
            val updated = map.values.sortedByDescending { meeting -> meeting.date }
            CoroutineScope(dispatchers.io).launch {
                meetingsFlow.emit(updated)
            }
        }
        val reference = references.getMeetingsReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        EventListener(reference, listener)
    }

    /**
     * UNPUBLISHED MEETINGS
     */
    override suspend fun getUnpublishedMeetings() = withContext(dispatchers.io) {
        references.getUnpublishedMeetingsReference().get().await()
            .children
            .map { snapshot -> snapshot.getValue<Meeting>()!! }
    }
}