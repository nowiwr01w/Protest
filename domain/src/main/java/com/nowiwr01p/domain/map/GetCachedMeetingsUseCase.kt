package com.nowiwr01p.domain.map

import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.usecase.GetMeetingsScreenCacheUseCase

class GetCachedMeetingsUseCase(
    private val meetingsScreenCacheUseCase: GetMeetingsScreenCacheUseCase
): UseCase<Unit, List<Meeting>> {

    override suspend fun execute(input: Unit): List<Meeting> {
        return meetingsScreenCacheUseCase.execute()?.data?.meetings.orEmpty()
    }
}