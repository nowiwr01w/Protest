package com.nowiwr01p.domain.meetings.create_meeting.usecase

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.create_meeting.validators.data.CreateMeetingError
import com.nowiwr01p.domain.meetings.create_meeting.validators.CreateMeetingValidator

class ValidateMeetingDataUseCase(
    private val validator: CreateMeetingValidator
): UseCase<Meeting, List<CreateMeetingError>> {

    override suspend fun execute(input: Meeting): List<CreateMeetingError> {
        return validator.validate(input)
    }
}