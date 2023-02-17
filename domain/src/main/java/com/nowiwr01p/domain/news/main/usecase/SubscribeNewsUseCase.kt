package com.nowiwr01p.domain.news.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.EventListener
import com.nowiwr01p.domain.news.main.repository.NewsRepository

class SubscribeNewsUseCase(
    private val client: NewsRepository
): UseCase<Unit, EventListener> {

    override suspend fun execute(input: Unit): EventListener {
        return client.subscribeNews()
    }
}