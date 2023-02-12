package com.nowiwr01p.data.profile

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.profile.repository.ProfileRepository
import com.nowiwr01p.domain.user.client.UserClient
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val auth: FirebaseAuth,
    private val client: UserClient,
    private val referencesRepository: FirebaseReferencesRepository,
    private val dispatchers: AppDispatchers
): ProfileRepository {

    /**
     * LOG OUT
     */
    override suspend fun logout() = withContext(dispatchers.io) {
        auth.signOut()
    }

    /**
     * DELETE ACCOUNT
     */
    override suspend fun deleteAccount(): Unit = withContext(dispatchers.io) {
        auth.currentUser!!.run {
            delete().await()
            referencesRepository.getUserReference(uid).removeValue().await()
        }
    }

    /**
     * USER IMAGE
     */
    override suspend fun uploadImage(uri: Uri) = withContext(dispatchers.io) {
        val user = client.getUserFlow().value
        clearPreviousImages(user.id)

        val fileName = "${user.id}/${uri.lastPathSegment}"
        val uploadReference = referencesRepository.getImagesStorageReference().child(fileName)

        uploadReference.putFile(uri).await()

        uploadReference.downloadUrl.await().toString()
    }

    private suspend fun clearPreviousImages(userId: String) {
        referencesRepository.getImagesStorageReference().child(userId).listAll().await()
            .items
            .forEach { it.delete().await() }
    }
}