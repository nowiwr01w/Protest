package com.nowiwr01p.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nowiwr01p.data.auth.repository.AuthRepositoryImpl
import com.nowiwr01p.data.auth.repository.ValidateAuthDataRepositoryImpl
import com.nowiwr01p.data.auth.validators.EmailValidatorImpl
import com.nowiwr01p.data.auth.validators.PasswordValidatorImpl
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.AppDispatchersImpl
import com.nowiwr01p.domain.auth.repository.AuthRepository
import com.nowiwr01p.domain.auth.repository.ValidateAuthDataRepository
import com.nowiwr01p.domain.auth.validators.EmailValidator
import com.nowiwr01p.domain.auth.validators.PasswordValidator
import org.koin.dsl.module

val moduleData = module {

    /**
     * DISPATCHERS
     */
    single<AppDispatchers> {
        AppDispatchersImpl()
    }

    /**
     * AUTH
     */
    factory<EmailValidator> {
        EmailValidatorImpl()
    }
    factory<PasswordValidator> {
        PasswordValidatorImpl()
    }
    factory<ValidateAuthDataRepository> {
        ValidateAuthDataRepositoryImpl(get(), get(), get())
    }
    factory<AuthRepository> {
        AuthRepositoryImpl(get(), get(), get())
    }

    /**
     * FIREBASE
     */
    factory {
        Firebase.auth
    }
    factory {
        Firebase.database
    }
}