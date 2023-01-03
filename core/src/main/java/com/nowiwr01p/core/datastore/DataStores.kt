package com.nowiwr01p.core.datastore

import android.content.Context
import androidx.datastore.dataStore
import com.nowiwr01p.core.datastore.auth.AuthSecurityWarningSerializer

enum class DataStoreType {
    AUTH_SECURITY
}

class AuthSecurityWarningDataStore(fileName: String) {

    private val Context.authSecurityWarningDataStore by dataStore(
        fileName = fileName,
        serializer = AuthSecurityWarningSerializer
    )

    fun create(context: Context) = context.authSecurityWarningDataStore
}