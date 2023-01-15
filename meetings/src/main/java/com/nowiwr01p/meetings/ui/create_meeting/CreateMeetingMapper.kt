package com.nowiwr01p.meetings.ui.create_meeting

import com.nowiwr01p.core.datastore.location.data.*
import com.nowiwr01p.core_ui.mapper.ViewModelMapper

class CreateMeetingMapper: ViewModelMapper<CreateMeetingVewModel>() {

    fun getMeeting() = with(viewModel.viewState.value) {
        Meeting(
            cityName = user.city.name,
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
                posters = posters
            ),
            details = Details(
                goals =  goals,
                slogans = slogans,
                strategy = strategy
            ),
            telegram = telegram
        )
    }
}