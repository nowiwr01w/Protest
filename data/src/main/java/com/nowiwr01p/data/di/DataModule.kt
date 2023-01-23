package com.nowiwr01p.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nowiwr01p.data.news.article.ArticleRepositoryImpl
import com.nowiwr01p.data.auth.main.repository.AuthRepositoryImpl
import com.nowiwr01p.data.auth.main.repository.ValidateAuthDataRepositoryImpl
import com.nowiwr01p.data.auth.main.validators.EmailValidatorImpl
import com.nowiwr01p.data.auth.main.validators.PasswordValidatorImpl
import com.nowiwr01p.data.meetings.create_meeting.CreateMeetingValidatorImpl
import com.nowiwr01p.data.firebase.FirebaseReferencesRepositoryImpl
import com.nowiwr01p.data.auth.cities.CitiesRepositoryImpl
import com.nowiwr01p.data.auth.cities.CitiesStateLocalRepositoryImpl
import com.nowiwr01p.data.auth.cities.CitiesStateRemoteRepositoryImpl
import com.nowiwr01p.data.news.create_article.repository.CreateArticleRepositoryImpl
import com.nowiwr01p.data.news.create_article.validator.CreateArticleValidatorImpl
import com.nowiwr01p.data.meetings.main.MeetingsRepositoryImpl
import com.nowiwr01p.data.news.main.NewsRepositoryImpl
import com.nowiwr01p.data.profile.ProfileRepositoryImpl
import com.nowiwr01p.data.user.UserRemoteRepositoryImpl
import com.nowiwr01p.data.auth.verification.VerificationRemoteRepositoryImpl
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.AppDispatchersImpl
import com.nowiwr01p.domain.news.article.ArticleRepository
import com.nowiwr01p.domain.auth.main.repository.AuthRepository
import com.nowiwr01p.domain.auth.main.repository.ValidateAuthDataRepository
import com.nowiwr01p.domain.auth.main.validators.EmailValidator
import com.nowiwr01p.domain.auth.main.validators.PasswordValidator
import com.nowiwr01p.domain.meetings.create_meeting.validators.CreateMeetingValidator
import com.nowiwr01p.domain.firebase.FirebaseReferencesRepository
import com.nowiwr01p.domain.auth.cities.repository.CityStateLocalRepository
import com.nowiwr01p.domain.auth.cities.repository.CitiesRepository
import com.nowiwr01p.domain.auth.cities.repository.CityStateRemoteRepository
import com.nowiwr01p.domain.news.create_article.repository.CreateArticleRepository
import com.nowiwr01p.domain.news.create_article.validators.CreateArticleValidator
import com.nowiwr01p.domain.meetings.main.repository.MeetingsRepository
import com.nowiwr01p.domain.news.main.repository.NewsRepository
import com.nowiwr01p.domain.profile.repository.ProfileRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRepository
import com.nowiwr01p.domain.auth.verification.repository.VerificationRemoteRepository
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
        ProfileRepositoryImpl(get(), get(), get(), get())
    }
}