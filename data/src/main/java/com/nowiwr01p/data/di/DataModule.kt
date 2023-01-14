package com.nowiwr01p.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nowiwr01p.data.auth.repository.AuthRepositoryImpl
import com.nowiwr01p.data.auth.repository.ValidateAuthDataRepositoryImpl
import com.nowiwr01p.data.auth.validators.EmailValidatorImpl
import com.nowiwr01p.data.auth.validators.PasswordValidatorImpl
import com.nowiwr01p.data.firebase.FirebaseReferencesRepositoryImpl
import com.nowiwr01p.data.location.LocationRepositoryImpl
import com.nowiwr01p.data.location.LocationStateLocalRepositoryImpl
import com.nowiwr01p.data.location.LocationStateRemoteRepositoryImpl
import com.nowiwr01p.data.meetings.MeetingsRepositoryImpl
import com.nowiwr01p.data.news.NewsRepositoryImpl
import com.nowiwr01p.data.user.UserRemoteRepositoryImpl
import com.nowiwr01p.data.verification.VerificationRemoteRepositoryImpl
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.AppDispatchersImpl
import com.nowiwr01p.domain.auth.repository.AuthRepository
import com.nowiwr01p.domain.auth.repository.ValidateAuthDataRepository
import com.nowiwr01p.domain.auth.validators.EmailValidator
import com.nowiwr01p.domain.auth.validators.PasswordValidator
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.location.repository.LocationStateLocalRepository
import com.nowiwr01p.domain.location.repository.LocationRepository
import com.nowiwr01p.domain.location.repository.LocationStateRemoteRepository
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository
import com.nowiwr01p.domain.news.repository.NewsRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import com.nowiwr01p.domain.verification.repository.VerificationRemoteRepository
import org.koin.dsl.module

val moduleData = module {

    /**
     * DISPATCHERS
     */
    single<AppDispatchers> {
        AppDispatchersImpl()
    }

    /**
     * USER
     */
    factory<UserRemoteRepository> {
        UserRemoteRepositoryImpl(get(), get(), get(), get(), get(), get())
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
    single<FirebaseReferencesRepository> {
        FirebaseReferencesRepositoryImpl(get())
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
     * VERIFICATION
     */
    factory<VerificationRemoteRepository> {
        VerificationRemoteRepositoryImpl(get(), get())
    }

    /**
     * LOCATION
     */
    factory<LocationRepository> {
        LocationRepositoryImpl(get(), get())
    }
    factory<LocationStateLocalRepository> {
        LocationStateLocalRepositoryImpl(get())
    }
    factory<LocationStateRemoteRepository> {
        LocationStateRemoteRepositoryImpl(get())
    }

    /**
     * MEETINGS
     */
    factory<MeetingsRepository> {
        MeetingsRepositoryImpl(get(), get(), get())
    }

    /**
     * NEWS
     */
    factory<NewsRepository> {
        NewsRepositoryImpl(get(), get())
    }
}