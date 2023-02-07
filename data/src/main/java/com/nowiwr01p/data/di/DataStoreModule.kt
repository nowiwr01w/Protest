package com.nowiwr01p.data.di

import com.google.gson.Gson
import com.nowiwr01p.core.BuildConfig
import com.nowiwr01p.core.datastore.*
import com.nowiwr01p.core.datastore.DataStoreType.*
import com.nowiwr01p.data.app.AppDataStoreRepositoryImpl
import com.nowiwr01p.data.auth.main.repository.AuthSecurityDataStoreRepositoryImpl
import com.nowiwr01p.data.auth.cities.CitiesStateLocalRepositoryImpl
import com.nowiwr01p.data.auth.verification.VerificationLocalRepositoryImpl
import com.nowiwr01p.domain.app.AppDataStoreRepository
import com.nowiwr01p.domain.auth.main.repository.AuthSecurityDataStoreRepository
import com.nowiwr01p.domain.auth.cities.api.CitiesApi
import com.nowiwr01p.domain.auth.cities.repository.CityStateLocalRepository
import com.nowiwr01p.domain.auth.verification.repository.VerificationLocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleCore = module {

    /**
     * NAMES
     */
    single(named(APP)) {
        BuildConfig.APP_SETTINGS_DATA_STORE
    }
    single(named(AUTH_SECURITY)) {
        BuildConfig.AUTH_SECURITY_DATA_STORE
    }
    single(named(CITIES)) {
        BuildConfig.LOCATION_DATA_STORE
    }
    single(named(VERIFICATION)) {
        BuildConfig.VERIFICATION_DATA_STORE
    }

    /**
     * INSTANCES
     */
    single<AppDataStoreRepository> {
        val fileName = get<String>(named(APP))
        val dataStore = AppDataStore(fileName)
        AppDataStoreRepositoryImpl(
            dataStore.create(androidContext())
        )
    }
    single<AuthSecurityDataStoreRepository> {
        val fileName = get<String>(named(AUTH_SECURITY))
        val dataStore = AuthSecurityWarningDataStore(fileName)
        AuthSecurityDataStoreRepositoryImpl(
            dataStore.create(androidContext())
        )
    }
    single<CityStateLocalRepository> {
        val fileName = get<String>(named(CITIES))
        val dataStore = CitiesDataStore(fileName)
        CitiesStateLocalRepositoryImpl(
            dataStore.create(androidContext())
        )
    }
    single<VerificationLocalRepository> {
        val fileName = get<String>(named(VERIFICATION))
        val dataStore = VerificationDataStore(fileName)
        VerificationLocalRepositoryImpl(
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
        CitiesApi(androidContext(), get())
    }
}