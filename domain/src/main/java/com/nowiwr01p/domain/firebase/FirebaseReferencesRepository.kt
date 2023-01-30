package com.nowiwr01p.domain.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

interface FirebaseReferencesRepository {
    suspend fun getUserReference(id: String): DatabaseReference
    suspend fun getNewsReference(): DatabaseReference
    suspend fun getStoriesReference(): DatabaseReference
    suspend fun getCategoriesReference(): DatabaseReference
    suspend fun getMeetingsReference(): DatabaseReference
    suspend fun getMeetingReference(id: String): DatabaseReference
    suspend fun getArticleReference(id: String): DatabaseReference
    suspend fun getLocationsReference(id: String): DatabaseReference
    suspend fun getImagesStorageReference(): StorageReference
}