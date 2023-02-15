package com.nowiwr01p.domain.app

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.config.AppRemoteConfig

class GetSplashScreenAnimationUrlUseCase(
    private val config: AppRemoteConfig,
    private val repository: AppDataStoreRepository
): UseCase<Unit, String> {

    override suspend fun execute(input: Unit) = repository.getAnimationUrl().also {
        config.initValues()
    }
}