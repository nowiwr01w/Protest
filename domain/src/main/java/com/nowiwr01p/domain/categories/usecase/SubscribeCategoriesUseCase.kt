package com.nowiwr01p.domain.categories.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.categories.client.CategoriesClient

class SubscribeCategoriesUseCase(
    private val client: CategoriesClient
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        client.subscribeCategories()
    }
}