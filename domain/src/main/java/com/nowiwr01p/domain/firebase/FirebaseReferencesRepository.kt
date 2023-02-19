package com.nowiwr01p.domain.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

interface FirebaseReferencesRepository {
    suspend fun getUserReference(id: String): DatabaseReference
    suspend fun getStoriesReference(): DatabaseReference
    suspend fun getCategoriesReference(): DatabaseReference
    suspend fun getNewsReference(): DatabaseReference
    suspend fun getNewsViewersReference(): DatabaseReference
    suspend fun getUnpublishedNewsReference(): DatabaseReference
    suspend fun getArticleReference(id: String): DatabaseReference
    suspend fun getArticlePreviewReference(id: String): DatabaseReference
    suspend fun getMeetingsReference(): DatabaseReference
    suspend fun getUnpublishedMeetingsReference(): DatabaseReference
    suspend fun getMeetingReference(id: String): DatabaseReference
    suspend fun getMeetingReactionReference(): DatabaseReference
    suspend fun getMeetingPreviewReference(id: String): DatabaseReference
    suspend fun getLocationsReference(id: String): DatabaseReference
    suspend fun getImagesStorageReference(): StorageReference
}