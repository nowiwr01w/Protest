package com.nowiwr01p.domain.di

import com.nowiwr01p.domain.auth.usecase.*
import com.nowiwr01p.domain.meetings.usecase.GetCategoriesUseCase
import com.nowiwr01p.domain.location.usecase.GetCitiesUseCase
import com.nowiwr01p.domain.location.usecase.GetCountriesUseCase
import com.nowiwr01p.domain.location.usecase.local.GetLocalCityUseCase
import com.nowiwr01p.domain.location.usecase.local.GetLocalCountryUseCase
import com.nowiwr01p.domain.location.usecase.local.SetCityUseCase
import com.nowiwr01p.domain.location.usecase.local.SetCountryUseCase
import com.nowiwr01p.domain.map.GetLocalUserUseCase
import com.nowiwr01p.domain.meetings.usecase.GetMeetingsUseCase
import com.nowiwr01p.domain.meetings.usecase.SetReactionUseCase
import com.nowiwr01p.domain.news.usecase.GetNewsUseCase
import com.nowiwr01p.domain.verification.usecase.GetRemoteVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.SendEmailVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.GetLocalVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.SetVerificationCompletedUseCase
import org.koin.dsl.module

val moduleDomain = module {

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
    factory { GetCountriesUseCase(get()) }
    factory { SetCityUseCase(get(), get()) }
    factory { SetCountryUseCase(get(), get()) }
    factory { GetLocalCityUseCase(get()) }
    factory { GetLocalCountryUseCase(get()) }

    /**
     * MAP
     */
    factory { GetLocalUserUseCase(get()) }

    /**
     * MEETINGS
     */
    factory { GetMeetingsUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { SetReactionUseCase(get()) }

    /**
     * NEWS
     */
    factory { GetNewsUseCase(get()) }
}