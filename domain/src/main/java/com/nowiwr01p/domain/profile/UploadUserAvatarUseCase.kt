package com.nowiwr01p.domain.profile

import android.net.Uri
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase

class UploadUserAvatarUseCase(
    private val profileRepository: ProfileRepository
): UseCase<Uri, User> {

    override suspend fun execute(input: Uri): User {
        return profileRepository.uploadImage(input)
    }
}