package com.nowiwr01p.domain.meetings.main.usecase

import com.nowiwr01p.core.datastore.cities.data.Reaction
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository
import kotlinx.coroutines.flow.StateFlow

class GetReactionsUseCase(
    private val repository: MeetingsRepository
): UseCase<Unit, StateFlow<Map<String, Reaction>>> {

    override suspend fun execute(input: Unit): StateFlow<Map<String, Reaction>> {
        return repository.getReactions()
    }
}