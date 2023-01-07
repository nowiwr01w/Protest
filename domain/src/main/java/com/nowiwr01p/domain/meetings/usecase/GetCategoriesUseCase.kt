package com.nowiwr01p.domain.meetings.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.data.Category
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository

class GetCategoriesUseCase(
    private val repository: MeetingsRepository
): UseCase<Unit, List<Category>> {

    override suspend fun execute(input: Unit): List<Category> {
        return repository.getCategories()
    }
}