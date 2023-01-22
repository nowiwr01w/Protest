package com.nowiwr01p.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nowiwr01p.data.article.ArticleRepositoryImpl
import com.nowiwr01p.data.auth.repository.AuthRepositoryImpl
import com.nowiwr01p.data.auth.repository.ValidateAuthDataRepositoryImpl
import com.nowiwr01p.data.auth.validators.EmailValidatorImpl
import com.nowiwr01p.data.auth.validators.PasswordValidatorImpl
import com.nowiwr01p.data.create_meeting.CreateMeetingValidatorImpl
import com.nowiwr01p.data.firebase.FirebaseReferencesRepositoryImpl
import com.nowiwr01p.data.cities.CitiesRepositoryImpl
import com.nowiwr01p.data.cities.CitiesStateLocalRepositoryImpl
import com.nowiwr01p.data.cities.CitiesStateRemoteRepositoryImpl
import com.nowiwr01p.data.create_article.repository.CreateArticleRepositoryImpl
import com.nowiwr01p.data.create_article.validator.CreateArticleValidatorImpl
import com.nowiwr01p.data.meetings.MeetingsRepositoryImpl
import com.nowiwr01p.data.news.NewsRepositoryImpl
import com.nowiwr01p.data.profile.ProfileRepositoryImpl
import com.nowiwr01p.data.user.UserRemoteRepositoryImpl
import com.nowiwr01p.data.verification.VerificationRemoteRepositoryImpl
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.AppDispatchersImpl
import com.nowiwr01p.domain.article.ArticleRepository
import com.nowiwr01p.domain.auth.repository.AuthRepository
import com.nowiwr01p.domain.auth.repository.ValidateAuthDataRepository
import com.nowiwr01p.domain.auth.validators.EmailValidator
import com.nowiwr01p.domain.auth.validators.PasswordValidator
import com.nowiwr01p.domain.cteate_meeting.validators.CreateMeetingValidator
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.cities.repository.CityStateLocalRepository
import com.nowiwr01p.domain.cities.repository.CitiesRepository
import com.nowiwr01p.domain.cities.repository.CityStateRemoteRepository
import com.nowiwr01p.domain.create_article.repository.CreateArticleRepository
import com.nowiwr01p.domain.create_article.validators.CreateArticleValidator
import com.nowiwr01p.domain.meetings.repository.MeetingsRepository
import com.nowiwr01p.domain.news.repository.NewsRepository
import com.nowiwr01p.domain.profile.ProfileRepository
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
    factory {
        Firebase.storage
    }
    single<FirebaseReferencesRepository> {
        FirebaseReferencesRepositoryImpl(get(), get())
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
    factory<CitiesRepository> {
        CitiesRepositoryImpl(get(), get())
    }
    factory<CityStateLocalRepository> {
        CitiesStateLocalRepositoryImpl(get())
    }
    factory<CityStateRemoteRepository> {
        CitiesStateRemoteRepositoryImpl(get())
    }

    /**
     * MEETINGS
     */
    factory<MeetingsRepository> {
        MeetingsRepositoryImpl(get(), get(), get())
    }

    /**
     * CREATE MEETING
     */
    factory<CreateMeetingValidator> {
        CreateMeetingValidatorImpl()
    }

    /**
     * NEWS
     */
    factory<NewsRepository> {
        NewsRepositoryImpl(get(), get())
    }

    /**
     * ARTICLE
     */
    factory<ArticleRepository> {
        ArticleRepositoryImpl(get(), get(), get())
    }

    /**
     * CREATE ARTICLE
     */
    factory<CreateArticleValidator> {
        CreateArticleValidatorImpl()
    }
    factory<CreateArticleRepository> {
        CreateArticleRepositoryImpl(get(), get())
    }

    /**
     * PROFILE
     */
    factory<ProfileRepository> {
        ProfileRepositoryImpl(get(), get(), get())
    }
}