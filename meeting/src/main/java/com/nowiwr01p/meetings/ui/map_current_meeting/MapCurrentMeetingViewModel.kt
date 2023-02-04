package com.nowiwr01p.meetings.ui.map_current_meeting

import androidx.lifecycle.viewModelScope
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.meetings.ui.map_current_meeting.MapCurrentMeetingContract.*
import com.nowiwr01p.meetings.ui.map_current_meeting.data.MeetingStatus
import com.nowiwr01p.meetings.ui.map_current_meeting.data.MeetingStatus.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapCurrentMeetingViewModel: BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meeting)
        }
    }

    private fun init(meeting: Meeting) {
        initMeeting(meeting)
        initMeetingStatus(meeting)
        initMeetingTitle()
    }

    /**
     * SET MEETING
     */
    private fun initMeeting(meeting: Meeting) {
        setState { copy(meeting = meeting) }
    }

    /**
     * SET MEETING STATUS
     */
    private fun initMeetingStatus(meeting: Meeting) {
        val status = MeetingStatus.get(meeting)
        setState { copy(meetingStatus = status) }
    }

    /**
     * SET MEETING TITLE
     */
    private fun initMeetingTitle() = with(viewState.value) {
        val title = when (meetingStatus) {
            WAITING_FOR_PEOPLE -> "Сбор людей".also { initWaitingTitle() }
            IN_PROGRESS -> "В процессе"
            ENDED -> "Митинг завершен"
        }
        setState { copy(title = title) }
    }

    /**
     * MAKE WAITING TITLE ANIMATED
     */
    private var waitingTitleJob: Job? = null

    private fun initWaitingTitle() {
        val animatedTitles =  listOf("Сбор людей", "Сбор людей.", "Сбор людей..", "Сбор людей...")
        waitingTitleJob?.cancel()
        waitingTitleJob = viewModelScope.launch {
            repeat(Int.MAX_VALUE) { index ->
                val title = animatedTitles[index % 4]
                setState { copy(title = title) }
                delay(750)
            }
        }
    }
}