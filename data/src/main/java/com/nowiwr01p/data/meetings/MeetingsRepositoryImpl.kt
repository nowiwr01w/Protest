package com.nowiwr01p.data.meetings

import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.datastore.cities.data.Meeting
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.core.model.Category
import com.nowiwr01p.domain.meetings.data.Story
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository
import com.nowiwr01p.domain.user.repository.UserDataStoreRepository
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MeetingsRepositoryImpl(
    private val references: FirebaseReferencesRepository,
    private val userDataStoreRepository: UserDataStoreRepository,
    private val dispatchers: AppDispatchers
): MeetingsRepository {

    /**
     * STORIES
     */
    override suspend fun getStories() = withContext(dispatchers.io) {
        val userId = userDataStoreRepository.getUser().id
        references.getStoriesReference().get().await()
            .children
            .map { snapshot -> snapshot.getValue<Story>()!! }
            .map { story -> story.copy(viewed = userId in story.viewers) }
            .sortedWith(
                compareBy( { story -> story.viewed }, { story -> story.priority } )
            )
    }

    /**
     * SET STORY VIEWED
     */
    override suspend fun setStoryViewed(storyId: String) = withContext(dispatchers.io) {
        val userId = userDataStoreRepository.getUser().id
        val updatedStory = references.getStoriesReference().child(storyId).get().await()
            .getValue<Story>()!!
            .updateStory(userId)
        references.getStoriesReference().child(storyId).setValue(updatedStory).await()
        updatedStory
    }

    private fun Story.updateStory(userId: String): Story {
        val updatedViewers = viewers.toMutableList().apply { add(userId) }
        return copy(viewed = true, viewers = updatedViewers)
    }

    /**
     * CATEGORIES
     */
    override suspend fun getCategories() = withContext(dispatchers.io) {
        references.getCategoriesReference().get().await()
            .children
            .map { snapshot -> snapshot.getValue<Category>()!! }
            .sortedBy { category -> category.priority }
    }

    /**
     * MEETINGS
     */
    override suspend fun getMeetings() = withContext(dispatchers.io) {
        val userCity = userDataStoreRepository.getUser().city.name
        references.getMeetingsReference().get().await()
            .children
            .map { snapshot -> snapshot.getValue<Meeting>()!! }
            .filter { meeting -> userCity == meeting.cityName }
            .sortedByDescending { meeting -> meeting.date }
    }

    /**
     * CREATE MEETING
     */
    override suspend fun createMeeting(meeting: Meeting): Unit = withContext(dispatchers.io) {
        references.getMeetingsReference()
            .child(meeting.id)
            .setValue(meeting)
            .await()
    }

    /**
     * REACTION
     */
    override suspend fun setReaction(meetingId: String, isPositiveButtonClicked: Boolean) = withContext(dispatchers.io) {
        val updatedMeeting = references.getMeetingsReference().child(meetingId).get().await()
            .getValue<Meeting>()!!
            .updateMeeting(isPositiveButtonClicked)
        references.getMeetingsReference().child(meetingId).setValue(updatedMeeting).await()
        updatedMeeting
    }

    private suspend fun Meeting.updateMeeting(positive: Boolean): Meeting {
        val userId = userDataStoreRepository.getUser().id

        val positiveContains = reaction.peopleGoCount.contains(userId)
        val maybeContains = reaction.peopleMaybeGoCount.contains(userId)

        val positiveReactionsFiltered = reaction.peopleGoCount
            .filterNot { userId == it }
            .toMutableList()
        val maybeReactionsFiltered = reaction.peopleMaybeGoCount
            .filterNot { userId == it }
            .toMutableList()

        if (positive && !positiveContains) positiveReactionsFiltered.add(userId)
        if (!positive && !maybeContains) maybeReactionsFiltered.add(userId)

        val updatedReaction = reaction.copy(
            peopleGoCount = positiveReactionsFiltered,
            peopleMaybeGoCount = maybeReactionsFiltered
        )
        return copy(reaction = updatedReaction)
    }
}