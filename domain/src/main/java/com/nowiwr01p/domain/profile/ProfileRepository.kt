package com.nowiwr01p.domain.profile

import android.net.Uri
import com.nowiwr01p.core.model.User

interface ProfileRepository {
    suspend fun uploadImage(uri: Uri): User
}