package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.usecase.data.MeetingsScreenCache
import com.nowiwr01p.domain.meetings.main.usecase.data.MeetingsScreenCacheData

class SaveMeetingsScreenCacheUseCase(
    private val meetingsCache: MeetingsScreenCache
): UseCase<MeetingsScreenCacheData, Unit> {

    override suspend fun execute(input: MeetingsScreenCacheData) {
        meetingsCache.data = input
    }
}