package com.nowiwr01p.domain.app

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.categories.usecase.SubscribeCategoriesUseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.meetings.main.usecase.SubscribeMeetingsUseCase
import com.nowiwr01p.domain.meetings.main.usecase.SubscribeReactionsUseCase
import com.nowiwr01p.domain.news.main.usecase.SubscribeNewsUseCase
import com.nowiwr01p.domain.stories.usecase.SubscribeStoriesUseCase
import com.nowiwr01p.domain.user.usecase.SubscribeUserUseCase
import kotlinx.coroutines.*

typealias ReferencedListener = Pair<DatabaseReference, ValueEventListener>

class InitAppDataUseCase(
    private val subscribeUserUseCase: SubscribeUserUseCase,
    private val subscribeNewsUseCase: SubscribeNewsUseCase,
    private val subscribeStoriesUseCase: SubscribeStoriesUseCase,
    private val subscribeMeetingsUseCase: SubscribeMeetingsUseCase,
    private val subscribeReactionsUseCase: SubscribeReactionsUseCase,
    private val subscribeCategoriesUseCase: SubscribeCategoriesUseCase,
    private val setupCrashlyticsUseCase: InitAppCrashlyticsUseCase,
    private val dispatchers: AppDispatchers
): UseCase<Unit, Unit> {

    private val referencedListeners = mutableListOf<ReferencedListener>()

    override suspend fun execute(input: Unit) {
        init()
    }

    private fun ReferencedListener.add() = referencedListeners.add(this)

    private suspend fun init() = CoroutineScope(dispatchers.io).launch {
        runCatching {
            listOf(
                async { setupCrashlyticsUseCase.execute() },
                async { subscribeUserUseCase.execute().add() },
                async { subscribeNewsUseCase.execute().add() },
                async { subscribeStoriesUseCase.execute().add() },
                async { subscribeMeetingsUseCase.execute().add() },
                async { subscribeReactionsUseCase.execute().add() },
                async { subscribeCategoriesUseCase.execute().add() },
            ).awaitAll()
        }
    }

    fun clearSubscribed() = CoroutineScope(dispatchers.io).launch {
        referencedListeners.map { referenceToListener ->
            async {
                with(referenceToListener) { first.removeEventListener(second) }
            }
        }.awaitAll()
    }
}