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
import com.nowiwr01p.core.datastore.cities.data.MeetingStatus.*
import com.nowiwr01p.core_ui.ui.button.ButtonState
import com.nowiwr01p.core_ui.ui.button.ButtonState.*
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.meeting.usecase.RunMeetingUseCase
import com.nowiwr01p.domain.user.usecase.GetUserUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapCurrentMeetingViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val subscribeMeetingUseCase: SubscribeMeetingUseCase,
    private val runMeetingUseCase: RunMeetingUseCase,
    private val showSnackBarHelper: ShowSnackBarHelper
): BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Init -> init(event.meeting)
            is Event.RunMeeting -> runMeeting()
        }
    }

    private fun init(meeting: Meeting) = io {
        getUserData()
        getLocation(meeting)
    }

    private suspend fun getUserData() = launch {
        getUserUseCase.execute().collect { user ->
            setState { copy(user = user) }
        }
    }

    /**
     * LOAD MEETING LOCATION AND PATH
     */
    private suspend fun getLocation(meeting: Meeting) = runCatching {
        getLocationUseCase.execute(meeting.id)
    }.onSuccess { location ->
        subscribeMeeting(meeting, location)
    }.onFailure {
        onGetLocationFailed()
    }

    private suspend fun onGetLocationFailed() {
        val params = SnackBarParams(text = "Упс, мы где-то налажали. Свяжитесь с нами, это важно.")
        showSnackBarHelper.showErrorSnackBar(params)
        delay(4000)
        setEffect { Effect.NavigateBack }
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
        val title = when (meeting.status) {
            WAITING_FOR_PEOPLE -> "Сбор людей".also { setWaitingTitle() }
            IN_PROGRESS -> "В процессе".also { cancelWaitingTitleJob() }
            ENDED -> "Митинг завершен".also { cancelWaitingTitleJob() }
        }
        setState {
            copy(
                title = title,
                meetingStatus = meeting.status,
                meeting = meeting.copy(locationInfo = location)
            )
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

    /**
     * SET MEETING "IN PROGRESS"
     */
    private fun runMeeting() = io {
        setState { copy(runMeetingButtonState = SEND_REQUEST) }
        runCatching {
            val location = viewState.value.meeting.locationInfo
            runMeetingUseCase.execute(location)
        }.onSuccess {
            onRunMeetingCompleted(SUCCESS)
        }.onFailure {
            onRunMeetingCompleted(ERROR)
        }
    }

    private suspend fun onRunMeetingCompleted(state: ButtonState) {
        setState { copy(runMeetingButtonState = state) }
        delay(3000)
        setState { copy(runMeetingButtonState = DEFAULT) }
    }
}