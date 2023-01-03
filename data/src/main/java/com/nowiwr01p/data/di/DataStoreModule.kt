package com.nowiwr01p.data.di

import com.google.gson.Gson
import com.nowiwr01p.core.datastore.AuthSecurityWarningDataStore
import com.nowiwr01p.core.BuildConfig
import com.nowiwr01p.core.datastore.DataStoreType
import com.nowiwr01p.core.datastore.LocationDataStore
import com.nowiwr01p.data.auth.repository.AuthSecurityDataStoreRepositoryImpl
import com.nowiwr01p.data.location.LocationPreferencesRepositoryImpl
import com.nowiwr01p.domain.auth.repository.AuthSecurityDataStoreRepository
import com.nowiwr01p.domain.location.api.LocationApi
import com.nowiwr01p.domain.location.repository.LocationPreferencesRepository
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
    single<LocationPreferencesRepository> {
        val fileName = get<String>(named(DataStoreType.LOCATION))
        val dataStore = LocationDataStore(fileName)
        LocationPreferencesRepositoryImpl(
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