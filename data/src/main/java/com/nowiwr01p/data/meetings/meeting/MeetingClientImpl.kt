package com.nowiwr01p.data.meetings.meeting

import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.meeting.client.MeetingClient
import com.nowiwr01p.core.datastore.cities.data.MeetingStatus.IN_PROGRESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MeetingClientImpl(
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): MeetingClient {

    private val meeting: MutableStateFlow<Meeting> = MutableStateFlow(Meeting())

    /**
     * SUBSCRIBE ON CURRENT MEETING
     */
    override suspend fun subscribeMeeting(id: String) = withContext(dispatchers.io) {
        val listener = createEventListener<Meeting> { curMeeting ->
            CoroutineScope(dispatchers.io).launch { meeting.emit(curMeeting) }
        }
        references.getMeetingReference(id).addValueEventListener(listener)
        meeting
    }

    /**
     * CHANGE MEETING STATUS
     */
    override suspend fun runMeeting(location: LocationInfo): Unit = withContext(dispatchers.io) {
        val updated = meeting.value.copy(
            status = IN_PROGRESS,
            currentPosition = location.locationStartPoint
        )
        references.getMeetingReference(meeting.value.id).setValue(updated).await()
    }
}