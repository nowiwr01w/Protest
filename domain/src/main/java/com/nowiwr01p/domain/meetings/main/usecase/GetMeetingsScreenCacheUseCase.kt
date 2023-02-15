package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.usecase.data.MeetingsScreenCache

class GetMeetingsScreenCacheUseCase(
    private val meetingsCache: MeetingsScreenCache
): UseCase<Unit, MeetingsScreenCache?> {

    override suspend fun execute(input: Unit): MeetingsScreenCache? {
        return if (meetingsCache.data.user.id.isNotEmpty()) meetingsCache else null
    }
}