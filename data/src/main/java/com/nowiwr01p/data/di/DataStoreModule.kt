package com.nowiwr01p.data.di

import com.nowiwr01p.core.AuthSecurityWarningDataStore
import com.nowiwr01p.core.BuildConfig
import com.nowiwr01p.core.DataStoreType
import com.nowiwr01p.data.auth.repository.AuthSecurityDataStoreRepositoryImpl
import com.nowiwr01p.domain.auth.repository.AuthSecurityDataStoreRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleDataStore = module {

    /**
     * NAMES
     */
    single(named(DataStoreType.AUTH_SECURITY)) {
        BuildConfig.AUTH_SECURITY_DATA_STORE
    }

    /**
     * INSTANCES
     */
    single<AuthSecurityDataStoreRepository> {
        val fileName = get<String>(named(DataStoreType.AUTH_SECURITY))
        val dataStore = AuthSecurityWarningDataStore(fileName)
        AuthSecurityDataStoreRepositoryImpl(
            dataStore.create(androidContext())
        )
    }
}