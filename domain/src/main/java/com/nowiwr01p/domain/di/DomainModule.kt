package com.nowiwr01p.domain.di

import com.nowiwr01p.domain.article.SetArticleViewedUseCase
import com.nowiwr01p.domain.auth.usecase.*
import com.nowiwr01p.domain.cteate_meeting.usecase.CreateMeetingUseCase
import com.nowiwr01p.domain.cteate_meeting.usecase.ValidateMeetingDataUseCase
import com.nowiwr01p.domain.meetings.usecase.GetCategoriesUseCase
import com.nowiwr01p.domain.cities.usecase.GetCitiesUseCase
import com.nowiwr01p.domain.cities.usecase.local.GetLocalCityUseCase
import com.nowiwr01p.domain.cities.usecase.local.SetCityUseCase
import com.nowiwr01p.domain.create_article.usecase.CreateArticleUseCase
import com.nowiwr01p.domain.create_article.usecase.ValidateArticleDataUseCase
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.meetings.usecase.GetMeetingsUseCase
import com.nowiwr01p.domain.meeting_info.SetReactionUseCase
import com.nowiwr01p.domain.meetingsScreenScopeId
import com.nowiwr01p.domain.meetingsScreenScopeName
import com.nowiwr01p.domain.news.usecase.GetNewsUseCase
import com.nowiwr01p.domain.profile.usecase.DeleteAccountUseCase
import com.nowiwr01p.domain.profile.usecase.LogOutUseCase
import com.nowiwr01p.domain.profile.usecase.UploadUserAvatarUseCase
import com.nowiwr01p.domain.user.usecase.GetRemoteUserUseCase
import com.nowiwr01p.domain.user.usecase.UpdateUserNameUseCase
import com.nowiwr01p.domain.verification.usecase.GetRemoteVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.SendEmailVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.GetLocalVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.SetVerificationCompletedUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleDomain = module {

    /**
     * USER
     */
    factory { GetRemoteUserUseCase(get()) }
    factory { UpdateUserNameUseCase(get(), get()) }

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
     * MAP
     */
    factory { GetLocalUserUseCase(get()) }

    /**
     * MEETINGS
     */
    factory { GetMeetingsUseCase(get()) }
    factory { SetReactionUseCase(get()) }

    factory {
        val scope = getKoin().getOrCreateScope(meetingsScreenScopeId, named(meetingsScreenScopeName))

        GetCategoriesUseCase(
            get(),
            scope.get()
        )
    }

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
    factory { UploadUserAvatarUseCase(get()) }
}