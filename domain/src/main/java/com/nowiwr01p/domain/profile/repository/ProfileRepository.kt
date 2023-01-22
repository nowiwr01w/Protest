package com.nowiwr01p.domain.profile.repository

import android.net.Uri
import com.nowiwr01p.core.model.User

interface ProfileRepository {
    suspend fun uploadImage(uri: Uri): User
    suspend fun logout()
    suspend fun deleteAccount()
}