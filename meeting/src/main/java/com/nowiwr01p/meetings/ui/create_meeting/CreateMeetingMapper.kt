package com.nowiwr01p.meetings.ui.create_meeting

import com.nowiwr01p.core.datastore.cities.data.*
import com.nowiwr01p.core_ui.mapper.ViewModelMapper
import java.util.*

class CreateMeetingMapper: ViewModelMapper<CreateMeetingVewModel>() {

    fun getMeeting() = with(viewModel.viewState.value) {
        Meeting(
            id = UUID.randomUUID().toString(),
            cityName = if (meetingEverywhere) "everywhere" else user.city.name,
            creatorId = user.id,
            image = imageLink,
            date = selectedDate,
            requiredPeopleCount = requiresPeopleCount.toIntOrNull() ?: 0,
            categories = selectedCategories.toList(),
            title = title,
            description = description,
            locationInfo = LocationInfo(
                locationName = location,
                locationStartPoint = Coordinate(startLocation.latitude, startLocation.longitude),
                locationDetails = locationDetails,
                path = path.map { Coordinate(it.latitude, it.longitude) }
            ),
            takeWithYouInfo = TakeWithYouInfo(
                postersMotivation = postersMotivation,
                posters = posters.filter { it.isNotEmpty() }
            ),
            details = Details(
                goals =  goals.filter { it.isNotEmpty() },
                slogans = slogans.filter { it.isNotEmpty() },
                strategy = strategy.filter { it.isNotEmpty() }
            ),
            telegram = telegram
        )
    }
}