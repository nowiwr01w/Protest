package com.nowiwr01p.domain.categories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.categories.repository.CategoriesRepository

class SubscribeCategoriesUseCase(
    private val client: CategoriesRepository
): UseCase<Unit, ReferencedListener> {

    override suspend fun execute(input: Unit): ReferencedListener {
        return client.subscribeCategories()
    }
}