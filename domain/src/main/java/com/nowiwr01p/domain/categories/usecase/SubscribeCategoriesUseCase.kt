package com.nowiwr01p.domain.categories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.categories.repository.CategoriesRepository

class SubscribeCategoriesUseCase(
    private val client: CategoriesRepository
): UseCase<Unit, EventListener> {

    override suspend fun execute(input: Unit): EventListener {
        return client.subscribeCategories()
    }
}