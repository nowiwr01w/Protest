package com.nowiwr01p.domain.firebase

import com.google.firebase.database.DatabaseReference

interface FirebaseReferencesRepository {
    suspend fun getUserReference(id: String): DatabaseReference
    suspend fun getNewsReference(): DatabaseReference
    suspend fun getCategoriesReference(): DatabaseReference
    suspend fun getMeetingsReference(): DatabaseReference
    suspend fun getMeetingsPreviewReference(): DatabaseReference
    suspend fun getArticleReference(id: String): DatabaseReference
}