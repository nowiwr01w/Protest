package com.nowiwr01p.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository

class FirebaseReferencesRepositoryImpl(
    private val storage: FirebaseStorage,
    private val database: FirebaseDatabase
): FirebaseReferencesRepository {

    override suspend fun getUserReference(id: String): DatabaseReference {
        return database.getReference(USER_REFERENCE).child(id)
    }

    override suspend fun getNewsReference(): DatabaseReference {
        return database.getReference(NEWS_REFERENCE)
    }

    override suspend fun getStoriesReference(): DatabaseReference {
        return database.getReference(STORIES_REFERENCE)
    }

    override suspend fun getCategoriesReference(): DatabaseReference {
        return database.getReference(CATEGORIES_REFERENCE)
    }

    override suspend fun getMeetingsReference(): DatabaseReference {
        return database.getReference(MEETINGS_REFERENCE)
    }

    override suspend fun getMeetingsPreviewReference(): DatabaseReference {
        return database.getReference(MEETINGS_PREVIEW_REFERENCE)
    }

    override suspend fun getArticleReference(id: String): DatabaseReference {
        return database.getReference(NEWS_REFERENCE).child(id)
    }

    override suspend fun getImagesStorageReference(): StorageReference {
        return storage.getReference(IMAGES_STORAGE)
    }

    private companion object {
        const val USER_REFERENCE = "users"
        const val NEWS_REFERENCE = "news"
        const val STORIES_REFERENCE = "stories"
        const val CATEGORIES_REFERENCE = "categories"
        const val MEETINGS_REFERENCE = "meetings"
        const val MEETINGS_PREVIEW_REFERENCE = "meetings_preview"
        const val IMAGES_STORAGE = "images"
    }
}