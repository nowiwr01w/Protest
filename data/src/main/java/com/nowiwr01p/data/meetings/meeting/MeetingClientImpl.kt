package com.nowiwr01p.data.meetings.meeting

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.meeting.client.MeetingClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeetingClientImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): MeetingClient {

    private val meeting: MutableStateFlow<Meeting> = MutableStateFlow(Meeting())

    override suspend fun getMeetingFlow() = meeting.asStateFlow()

    override suspend fun subscribeMeeting(id: String): Unit = withContext(dispatchers.io) {
        val listener = createEventListener<Meeting> { curMeeting ->
            CoroutineScope(dispatchers.io).launch {
                meeting.emit(curMeeting)
            }
        }
        references.getMeetingReference(id).addValueEventListener(listener)
    }
}