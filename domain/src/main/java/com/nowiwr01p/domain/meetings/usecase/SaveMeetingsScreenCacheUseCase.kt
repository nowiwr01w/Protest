package com.nowiwr01p.domain.meetings.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.usecase.data.MeetingsScreenCache
import com.nowiwr01p.domain.meetings.usecase.data.MeetingsScreenCacheData

class SaveMeetingsScreenCacheUseCase(
    private val meetingsCache: MeetingsScreenCache
): UseCase<MeetingsScreenCacheData, Unit> {

    override suspend fun execute(input: MeetingsScreenCacheData) {
        meetingsCache.data = input
    }
}