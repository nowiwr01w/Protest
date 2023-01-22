package com.nowiwr01p.data.profile

import android.net.Uri
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.profile.ProfileRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImpl(
    private val remoteRepository: UserRemoteRepository,
    private val referencesRepository: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): ProfileRepository {

    override suspend fun uploadImage(uri: Uri) = with(dispatchers.io) {
        val user = remoteRepository.getUser()
        val fileName = "${user.id}/${System.currentTimeMillis()}/${uri.lastPathSegment}"
        val uploadReference = referencesRepository.getImagesStorageReference().child(fileName)

        uploadReference.putFile(uri).await()

        val link = uploadReference.downloadUrl.await().toString()

        val updatedUser = user.copy(avatar = link)
        remoteRepository.updateUser(updatedUser)
    }
}