package com.nowiwr01p.core.datastore

import android.content.Context
import androidx.datastore.dataStore
import com.nowiwr01p.core.datastore.app.AppPreferencesSerializer
import com.nowiwr01p.core.datastore.auth.AuthSecurityWarningSerializer
import com.nowiwr01p.core.datastore.cities.CitiesPreferencesSerializer
import com.nowiwr01p.core.datastore.verification.VerificationPreferenceSerializer

enum class DataStoreType {
    APP,
    AUTH_SECURITY,
    CITIES,
    VERIFICATION,
}

class AppDataStore(fileName: String) {

    private val Context.appDataStore by dataStore(
        fileName = fileName,
        serializer = AppPreferencesSerializer
    )

    fun create(context: Context) = context.appDataStore
}

class AuthSecurityWarningDataStore(fileName: String) {

    private val Context.authSecurityWarningDataStore by dataStore(
        fileName = fileName,
        serializer = AuthSecurityWarningSerializer
    )

    fun create(context: Context) = context.authSecurityWarningDataStore
}

class CitiesDataStore(fileName: String) {

    private val Context.citiesDataStore by dataStore(
        fileName = fileName,
        serializer = CitiesPreferencesSerializer
    )

    fun create(context: Context) = context.citiesDataStore
}

class VerificationDataStore(fileName: String) {

    private val Context.verificationDataStore by dataStore(
        fileName = fileName,
        serializer = VerificationPreferenceSerializer
    )

    fun create(context: Context) = context.verificationDataStore
}