package com.nowiwr01p.domain.user.usecase

import android.net.Uri
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.execute
import com.nowiwr01p.domain.profile.repository.ProfileRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRepository

class UpdateUserAvatarUseCase(
    private val profileRepository: ProfileRepository,
    private val getUserUseCase: GetUserUseCase,
    private val userRemoteRepository: UserRemoteRepository
): UseCase<Uri, Unit> {

    override suspend fun execute(input: Uri) {
        val link = profileRepository.uploadImage(input)
        val updated = getUserUseCase.execute().value.copy(avatar = link)
        userRemoteRepository.setUser(updated)
    }
}