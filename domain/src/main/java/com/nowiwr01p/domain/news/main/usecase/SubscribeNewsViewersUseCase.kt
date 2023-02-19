package com.nowiwr01p.domain.news.main.usecase

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.news.main.repository.NewsRepository

class SubscribeNewsViewersUseCase(
    private val repository: NewsRepository
): UseCase<Unit, ReferencedListener> {

    override suspend fun execute(input: Unit): ReferencedListener {
        return repository.subscribeNewsViewers()
    }
}