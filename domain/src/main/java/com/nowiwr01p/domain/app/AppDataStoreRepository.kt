package com.nowiwr01p.domain.app

interface AppDataStoreRepository {
    suspend fun isSplashScreenDemoAnimation(): Boolean
    suspend fun setSplashScreenDemoAnimation(demo: Boolean)
}