package com.nowiwr01p.domain.categories.usecase

import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.categories.repository.CategoriesRepository
import kotlinx.coroutines.flow.StateFlow

class GetCategoriesUseCase(
    private val client: CategoriesRepository
): UseCase<Unit, StateFlow<List<Category>>> {

    override suspend fun execute(input: Unit): StateFlow<List<Category>> {
        return client.getCategories()
    }
}