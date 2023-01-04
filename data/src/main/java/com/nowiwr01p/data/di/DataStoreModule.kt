package com.nowiwr01p.data.di

import com.google.gson.Gson
import com.nowiwr01p.core.datastore.AuthSecurityWarningDataStore
import com.nowiwr01p.core.BuildConfig
import com.nowiwr01p.core.datastore.DataStoreType
import com.nowiwr01p.core.datastore.LocationDataStore
import com.nowiwr01p.core.datastore.VerificationDataStore
import com.nowiwr01p.data.auth.repository.AuthSecurityDataStoreRepositoryImpl
import com.nowiwr01p.data.location.LocationDataStoreRepositoryImpl
import com.nowiwr01p.data.verification.VerificationDataStoreRepositoryImpl
import com.nowiwr01p.domain.auth.repository.AuthSecurityDataStoreRepository
import com.nowiwr01p.domain.location.api.LocationApi
import com.nowiwr01p.domain.location.repository.LocationDataStoreRepository
import com.nowiwr01p.domain.verification.repository.VerificationDataStoreRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleCore = module {

    /**
     * NAMES
     */
    single(named(DataStoreType.AUTH_SECURITY)) {
        BuildConfig.AUTH_SECURITY_DATA_STORE
    }
    single(named(DataStoreType.LOCATION)) {
        BuildConfig.LOCATION_DATA_STORE
    }
    single(named(DataStoreType.VERIFICATION)) {
        BuildConfig.VERIFICATION_DATA_STORE
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
    single<LocationDataStoreRepository> {
        val fileName = get<String>(named(DataStoreType.LOCATION))
        val dataStore = LocationDataStore(fileName)
        LocationDataStoreRepositoryImpl(
            dataStore.create(androidContext())
        )
    }
    single<VerificationDataStoreRepository> {
        val fileName = get<String>(named(DataStoreType.VERIFICATION))
        val dataStore = VerificationDataStore(fileName)
        VerificationDataStoreRepositoryImpl(
            dataStore.create(androidContext())
        )
    }

    /**
     * LOCATIONS
     */
    factory {
        Gson()
    }
    single {
        LocationApi(androidContext(), get())
    }
}