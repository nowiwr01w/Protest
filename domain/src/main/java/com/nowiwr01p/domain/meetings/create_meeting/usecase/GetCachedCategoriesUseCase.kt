package com.nowiwr01p.domain.meetings.create_meeting.usecase

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.main.usecase.GetMeetingsScreenCacheUseCase

class GetCachedCategoriesUseCase(
    private val meetingsScreenCacheUseCase: GetMeetingsScreenCacheUseCase
): UseCase<Unit, List<Category>> {

    override suspend fun execute(input: Unit): List<Category> {
        return meetingsScreenCacheUseCase.execute()?.data?.categories.orEmpty()
    }
}