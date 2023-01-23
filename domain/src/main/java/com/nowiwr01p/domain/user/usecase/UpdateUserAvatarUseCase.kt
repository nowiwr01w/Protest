package com.nowiwr01p.domain.user.usecase

import android.net.Uri
import com.nowiwr01p.core.model.User
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.profile.repository.ProfileRepository

class UpdateUserAvatarUseCase(
    private val profileRepository: ProfileRepository
): UseCase<Uri, User> {

    override suspend fun execute(input: Uri): User {
        return profileRepository.uploadImage(input)
    }
}