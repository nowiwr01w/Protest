package com.nowiwr01p.domain.meetings.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.cteate_meeting.usecase.GetCachedCategoriesUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository

class GetCategoriesUseCase(
    private val repository: MeetingsRepository,
    private val getCachedCategoriesUseCase: GetCachedCategoriesUseCase
): UseCase<Unit, List<Category>> {

    override suspend fun execute(input: Unit) = getCachedCategoriesUseCase.execute().let {
        it.ifEmpty { repository.getCategories() }
    }
}