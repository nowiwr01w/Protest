package com.nowiwr01p.domain.app

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nowiwr01p.domain.BuildConfig
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.user.repository.UserRemoteRepository

class InitAppCrashlyticsUseCase(
    private val repository: UserRemoteRepository,
    private val crashlytics: FirebaseCrashlytics
): UseCase<Unit, Unit> {

    override suspend fun execute(input: Unit) {
        val userId = repository.getFirebaseUser().uid
        crashlytics.setUserId(userId)
        crashlytics.setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}