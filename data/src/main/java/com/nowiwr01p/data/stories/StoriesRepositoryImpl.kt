package com.nowiwr01p.data.stories

import com.google.firebase.database.GenericTypeIndicator
import com.nowiwr01p.core.extenstion.createEventListener
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.app.ReferencedListener
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.meetings.main.data.Story
import com.nowiwr01p.domain.stories.repository.StoriesRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRealtimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class StoriesRepositoryImpl(
    private val userRemoteRealtimeRepository: UserRemoteRealtimeRepository,
    private val references: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): StoriesRepository {

    /**
     * GET STORIES
     */
    private val storiesFlow = MutableStateFlow(listOf<Story>())

    override suspend fun getStories() = storiesFlow

    override suspend fun subscribeStories() = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, Story>> { map ->
            storiesFlow.emit(map.values.toList())
        }
        val reference = references.getStoriesReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        ReferencedListener(reference, listener)
    }

    /**
     * STORIES VIEWERS
     */
    private val storiesViewersFlow = MutableStateFlow(mapOf<String, List<String>>())

    override suspend fun getStoriesViewers() = storiesViewersFlow

    override suspend fun subscribeStoriesViewers() = withContext(dispatchers.io) {
        val listener = createEventListener<Map<String, List<String>>> { map ->
            storiesViewersFlow.emit(map)
        }
        val reference = references.getStoriesViewersReference().also { ref ->
            ref.addValueEventListener(listener)
        }
        ReferencedListener(reference, listener)
    }

    /**
     * SET STORY VIEWED
     */
    override suspend fun setStoryViewed(storyId: String): Unit = withContext(dispatchers.io) {
        val userId = userRemoteRealtimeRepository.getUserFlow().value.id
        val updatedViewers = references.getStoriesViewersReference().child(storyId)
            .get()
            .await()
            .getValue(LIST_STRING_TOKEN)
            .orEmpty()
            .updateStory(userId)
        references.getStoriesViewersReference()
            .child(storyId)
            .setValue(updatedViewers)
            .await()
    }

    private fun List<String>.updateStory(userId: String): List<String> {
        val updatedViewers = toMutableList().apply { add(userId) }
        return updatedViewers.toList()
    }

    private companion object {
        val LIST_STRING_TOKEN = object : GenericTypeIndicator<List<String>>() {}
    }
}