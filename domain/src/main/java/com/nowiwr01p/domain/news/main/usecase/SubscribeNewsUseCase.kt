package com.nowiwr01p.domain.news.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.news.main.repository.NewsClient

class SubscribeNewsUseCase(
    private val client: NewsClient
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        client.subscribeNews()
    }
}