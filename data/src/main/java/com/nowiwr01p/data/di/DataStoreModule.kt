package com.nowiwr01p.data.di

import com.google.gson.Gson
import com.nowiwr01p.core.BuildConfig
import com.nowiwr01p.core.datastore.*
import com.nowiwr01p.data.auth.main.repository.AuthSecurityDataStoreRepositoryImpl
import com.nowiwr01p.data.auth.cities.CitiesStateLocalRepositoryImpl
import com.nowiwr01p.data.user.UserLocalRepositoryImpl
import com.nowiwr01p.data.auth.verification.VerificationLocalRepositoryImpl
import com.nowiwr01p.domain.auth.main.repository.AuthSecurityDataStoreRepository
import com.nowiwr01p.domain.auth.cities.api.CitiesApi
import com.nowiwr01p.domain.auth.cities.repository.CityStateLocalRepository
import com.nowiwr01p.domain.user.repository.UserLocalRepository
import com.nowiwr01p.domain.auth.verification.repository.VerificationLocalRepository
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
    single(named(DataStoreType.CITIES)) {
        BuildConfig.LOCATION_DATA_STORE
    }
    single(named(DataStoreType.VERIFICATION)) {
        BuildConfig.VERIFICATION_DATA_STORE
    }
    single(named(DataStoreType.USER)) {
        BuildConfig.USER_DATA_STORE
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
    single<CityStateLocalRepository> {
        val fileName = get<String>(named(DataStoreType.CITIES))
        val dataStore = CitiesDataStore(fileName)
        CitiesStateLocalRepositoryImpl(
            dataStore.create(androidContext())
        )
    }
    single<VerificationLocalRepository> {
        val fileName = get<String>(named(DataStoreType.VERIFICATION))
        val dataStore = VerificationDataStore(fileName)
        VerificationLocalRepositoryImpl(
            dataStore.create(androidContext())
        )
    }
    single<UserLocalRepository> {
        val fileName = get<String>(named(DataStoreType.USER))
        val dataStore = UserDataStore(fileName)
        UserLocalRepositoryImpl(
            dataStore.create(androidContext()),
            get()
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