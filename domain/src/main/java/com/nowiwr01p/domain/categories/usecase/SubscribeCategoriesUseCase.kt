package com.nowiwr01p.domain.categories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.categories.client.CategoriesClient

class SubscribeCategoriesUseCase(
    private val client: CategoriesClient
): UseCase<Unit, EventListener> {

    override suspend fun execute(input: Unit): EventListener {
        return client.subscribeCategories()
    }
}