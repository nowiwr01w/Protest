package com.nowiwr01p.domain.di

import com.nowiwr01p.domain.auth.cities.usecase.GetCitiesUseCase
import com.nowiwr01p.domain.auth.cities.usecase.local.GetLocalCityUseCase
import com.nowiwr01p.domain.auth.cities.usecase.local.SetCityUseCase
import com.nowiwr01p.domain.auth.main.usecase.*
import com.nowiwr01p.domain.auth.verification.usecase.GetLocalVerificationUseCase
import com.nowiwr01p.domain.auth.verification.usecase.GetRemoteVerificationUseCase
import com.nowiwr01p.domain.auth.verification.usecase.SendEmailVerificationUseCase
import com.nowiwr01p.domain.auth.verification.usecase.SetVerificationCompletedUseCase
import com.nowiwr01p.domain.meetings.create_meeting.usecase.CreateMeetingUseCase
import com.nowiwr01p.domain.meetings.create_meeting.usecase.ValidateMeetingDataUseCase
import com.nowiwr01p.domain.meetings.main.usecase.GetCategoriesUseCase
import com.nowiwr01p.domain.meetings.main.usecase.GetMeetingsUseCase
import com.nowiwr01p.domain.meetings.main.usecase.GetStoriesUseCase
import com.nowiwr01p.domain.meetings.main.usecase.SetStoryViewedUseCase
import com.nowiwr01p.domain.meetings.meeting.SetReactionUseCase
import com.nowiwr01p.domain.news.article.SetArticleViewedUseCase
import com.nowiwr01p.domain.news.create_article.usecase.CreateArticleUseCase
import com.nowiwr01p.domain.news.create_article.usecase.ValidateArticleDataUseCase
import com.nowiwr01p.domain.news.main.usecase.GetNewsUseCase
import com.nowiwr01p.domain.profile.usecase.DeleteAccountUseCase
import com.nowiwr01p.domain.profile.usecase.LogOutUseCase
import com.nowiwr01p.domain.user.usecase.GetUserUseCase
import com.nowiwr01p.domain.user.usecase.SubscribeUserUseCase
import com.nowiwr01p.domain.user.usecase.UpdateUserAvatarUseCase
import com.nowiwr01p.domain.user.usecase.UpdateUserNameUseCase
import org.koin.dsl.module

val moduleDomain = module {

    /**
     * USER
     */
    factory { GetUserUseCase(get()) }
    factory { SubscribeUserUseCase(get()) }
    factory { UpdateUserNameUseCase(get(), get()) }
    factory { UpdateUserAvatarUseCase(get(), get(), get()) }

    /**
     * AUTH
     */
    factory { ValidateAuthDataUseCase(get()) }
    factory { GetAuthSecurityWarningUseCase(get()) }
    factory { SetAuthSecurityWarningShownUseCase(get()) }
    factory { SignInUseCase(get()) }
    factory { SignUpUseCase(get()) }

    /**
     * VERIFICATION
     */
    factory { GetLocalVerificationUseCase(get()) }
    factory { SetVerificationCompletedUseCase(get(), get()) }
    factory { SendEmailVerificationUseCase(get()) }
    factory { GetRemoteVerificationUseCase(get()) }

    /**
     * LOCATION
     */
    factory { GetCitiesUseCase(get()) }
    factory { SetCityUseCase(get(), get()) }
    factory { GetLocalCityUseCase(get()) }

    /**
     * MEETINGS
     */
    factory { GetStoriesUseCase(get()) }
    factory { GetMeetingsUseCase(get()) }
    factory { SetReactionUseCase(get()) }
    factory { SetStoryViewedUseCase(get()) }

    factory { GetCategoriesUseCase(get()) }

    /**
     * CREATE MEETING
     */
    factory { CreateMeetingUseCase(get()) }
    factory { ValidateMeetingDataUseCase(get()) }

    /**
     * NEWS
     */
    factory { GetNewsUseCase(get()) }

    /**
     * ARTICLE
     */
    factory { SetArticleViewedUseCase(get()) }

    /**
     * CREATE ARTICLE
     */
    factory { CreateArticleUseCase(get()) }
    factory { ValidateArticleDataUseCase(get()) }

    /**
     * PROFILE
     */
    factory { LogOutUseCase(get()) }
    factory { DeleteAccountUseCase(get()) }
}