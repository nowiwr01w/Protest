package com.nowiwr01p.domain.app

interface AppDataStoreRepository {
    suspend fun getAnimationUrl(): String
    suspend fun setAnimationUrl(url: String)
}