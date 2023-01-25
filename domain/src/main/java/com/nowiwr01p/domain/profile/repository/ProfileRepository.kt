package com.nowiwr01p.domain.profile.repository

import android.net.Uri

interface ProfileRepository {
    suspend fun uploadImage(uri: Uri): String
    suspend fun logout()
    suspend fun deleteAccount()
}