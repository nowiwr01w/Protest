package com.nowiwr01p.domain.app

import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.categories.usecase.SubscribeCategoriesUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.main.usecase.SubscribeMeetingsUseCase
import com.nowiwr01p.domain.news.main.usecase.SubscribeNewsUseCase
import com.nowiwr01p.domain.stories.usecase.SubscribeStoriesUseCase
import com.nowiwr01p.domain.user.usecase.SubscribeUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class InitAppDataUseCase(
    private val subscribeUserUseCase: SubscribeUserUseCase,
    private val subscribeStoriesUseCase: SubscribeStoriesUseCase,
    private val subscribeNewsUseCase: SubscribeNewsUseCase,
    private val subscribeMeetingsUseCase: SubscribeMeetingsUseCase,
    private val subscribeCategoriesUseCase: SubscribeCategoriesUseCase,
    private val setupCrashlyticsUseCase: InitAppCrashlyticsUseCase,
    private val dispatchers: AppDispatchers
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        init()
    }

    private suspend fun init() = CoroutineScope(dispatchers.io).launch {
        runCatching {
            listOf(
                async { subscribeUserUseCase.execute() },
                async { subscribeStoriesUseCase.execute() },
                async { subscribeMeetingsUseCase.execute() },
                async { subscribeCategoriesUseCase.execute() },
                async { subscribeNewsUseCase.execute() },
                async { setupCrashlyticsUseCase.execute() }
            ).awaitAll()
        }
    }
}