package com.nowiwr01p.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.storage.ktx.storage
import com.nowiwr01p.core.BuildConfig
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
import com.nowiwr01p.data.user.repository.UserRemoteRepositoryImpl
import com.nowiwr01p.data.auth.verification.VerificationRemoteRepositoryImpl
import com.nowiwr01p.data.categories.CategoriesRepositoryImpl
import com.nowiwr01p.domain.config.AppRemoteConfig
import com.nowiwr01p.data.config.CreateArticleRemoteConfigImpl
import com.nowiwr01p.data.config.CreateMeetingRemoteConfigImpl
import com.nowiwr01p.data.meetings.create_meeting.CreateMeetingRepositoryImpl
import com.nowiwr01p.data.meetings.meeting.MeetingRepositoryImpl
import com.nowiwr01p.data.stories.StoriesRepositoryImpl
import com.nowiwr01p.data.user.client.UserRemoteRealtimeRepositoryImpl
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
import com.nowiwr01p.domain.categories.repository.CategoriesRepository
import com.nowiwr01p.domain.config.CreateArticleRemoteConfig
import com.nowiwr01p.domain.config.CreateMeetingRemoteConfig
import com.nowiwr01p.domain.meetings.create_meeting.repository.CreateMeetingRepository
import com.nowiwr01p.domain.meetings.meeting.repository.MeetingRepository
import com.nowiwr01p.domain.stories.repository.StoriesRepository
import com.nowiwr01p.domain.user.repository.UserRemoteRealtimeRepository
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
        UserRemoteRepositoryImpl(get(), get(), get(), get())
    }
    single<UserRemoteRealtimeRepository> {
        UserRemoteRealtimeRepositoryImpl(get(), get(), get(), get())
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
    factory {
        Firebase.crashlytics
    }
    single {
        Firebase.remoteConfig.apply {
            val interval = if (BuildConfig.DEBUG) 0 else 900
            setConfigSettingsAsync(
                remoteConfigSettings { minimumFetchIntervalInSeconds = interval.toLong() }
            )
        }
    }
    single {
        AppRemoteConfig(get())
    }
    single<CreateArticleRemoteConfig> {
        CreateArticleRemoteConfigImpl()
    }
    single<CreateMeetingRemoteConfig> {
        CreateMeetingRemoteConfigImpl()
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
        CitiesStateRemoteRepositoryImpl(get(), get())
    }

    /**
     * MEETINGS
     */
    single<MeetingsRepository> {
        MeetingsRepositoryImpl(get(), get())
    }

    /**
     * CATEGORIES
     */
    single<CategoriesRepository> {
        CategoriesRepositoryImpl(get() ,get())
    }

    /**
     * STORIES
     */
    single<StoriesRepository> {
        StoriesRepositoryImpl(get(), get(), get())
    }

    /**
     * MEETING
     */
    single<MeetingRepository> {
        MeetingRepositoryImpl(get(), get(), get())
    }

    /**
     * CREATE MEETING
     */
    factory<CreateMeetingRepository> {
        CreateMeetingRepositoryImpl(get(), get(), get())
    }
    factory<CreateMeetingValidator> {
        CreateMeetingValidatorImpl(get())
    }

    /**
     * NEWS
     */
    single<NewsRepository> {
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
        CreateArticleValidatorImpl(get())
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