package com.nowiwr01p.meetings.ui.map_current_meeting

import androidx.lifecycle.viewModelScope
import com.nowiwr01p.core.datastore.cities.data.LocationInfo
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.core_ui.ui.snack_bar.SnackBarParams
import com.nowiwr01p.core_ui.view_model.BaseViewModel
import com.nowiwr01p.domain.meetings.meeting.usecase.GetLocationUseCase
import com.nowiwr01p.domain.meetings.meeting.usecase.SubscribeMeetingUseCase
import com.nowiwr01p.meetings.ui.map_current_meeting.MapCurrentMeetingContract.*
import com.nowiwr01p.meetings.ui.map_current_meeting.data.MeetingStatus
import com.nowiwr01p.meetings.ui.map_current_meeting.data.MeetingStatus.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapCurrentMeetingViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val subscribeMeetingUseCase: SubscribeMeetingUseCase,
    private val showSnackBarHelper: ShowSnackBarHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meeting)
        }
    }

    private fun init(meeting: Meeting) {
        getLocation(meeting)
    }

    private fun getLocation(meeting: Meeting) = io {
        runCatching {
            getLocationUseCase.execute(meeting.id)
        }.onSuccess { location ->
            subscribeMeeting(meeting, location)
        }.onFailure {
            val params = SnackBarParams(text = "Упс, мы где-то налажали. Свяжитесь с нами, это важно.")
            showSnackBarHelper.showErrorSnackBar(params)
            delay(4000)
            setEffect { Effect.NavigateBack }
        }
    }

    /**
     * SUBSCRIBE MEETING
     */
    private fun subscribeMeeting(meeting: Meeting, location: LocationInfo) = io {
        runCatching {
            subscribeMeetingUseCase.execute(meeting.id)
        }.onSuccess { meetingFlow ->
            meetingFlow.collect { subscribed -> setMeetingData(subscribed, location) }
        }
    }

    /**
     * SET MEETING DATA
     */
    private fun setMeetingData(meeting: Meeting, location: LocationInfo) {
        MeetingStatus.get(meeting).let { status ->
            val title = when (status) {
                WAITING_FOR_PEOPLE -> "Сбор людей".also { setWaitingTitle() }
                IN_PROGRESS -> "В процессе".also { cancelWaitingTitleJob() }
                ENDED -> "Митинг завершен".also { cancelWaitingTitleJob() }
            }
            setState {
                copy(
                    title = title,
                    meetingStatus = status,
                    meeting = meeting.copy(locationInfo = location)
                )
            }
        }
    }

    /**
     * MAKE WAITING TITLE ANIMATED
     */
    private var waitingTitleJob: Job? = null

    private fun setWaitingTitle() {
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

    private fun cancelWaitingTitleJob() {
        waitingTitleJob?.cancel()
    }
}