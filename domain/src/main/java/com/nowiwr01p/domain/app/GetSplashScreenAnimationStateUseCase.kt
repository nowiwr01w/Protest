package com.nowiwr01p.domain.app

import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.config.AppRemoteConfig

class GetSplashScreenAnimationStateUseCase(
    private val config: AppRemoteConfig,
    private val repository: AppDataStoreRepository
): UseCase<Unit, Boolean> {

    override suspend fun execute(input: Unit) = repository.isSplashScreenDemoAnimation().also {
        config.initValues()
    }
}