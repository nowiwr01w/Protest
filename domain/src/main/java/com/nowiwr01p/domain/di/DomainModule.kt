package com.nowiwr01p.domain.di

import com.nowiwr01p.domain.auth.usecase.*
import com.nowiwr01p.domain.location.usecase.GetCitiesUseCase
import com.nowiwr01p.domain.location.usecase.GetCountriesUseCase
import com.nowiwr01p.domain.location.usecase.local.IsCitySetUseCase
import com.nowiwr01p.domain.location.usecase.local.IsCountrySetUseCase
import com.nowiwr01p.domain.location.usecase.local.SetCityUseCase
import com.nowiwr01p.domain.location.usecase.local.SetCountryUseCase
import com.nowiwr01p.domain.verification.usecase.FirebaseCheckEmailVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.FirebaseSendVerificationUseCase
import com.nowiwr01p.domain.verification.usecase.IsVerificationCompletedUseCase
import com.nowiwr01p.domain.verification.usecase.SetVerificationCompletedUseCase
import org.koin.dsl.module

val moduleDomain = module  {

    /**
     * AUTH
     */
    factory { ValidateAuthDataUseCase(get()) }
    factory { GetAuthSecurityWarningUseCase(get()) }
    factory { SetAuthSecurityWarningShownUseCase(get()) }
    factory { FirebaseSignInUseCase(get()) }
    factory { FirebaseSignUpUseCase(get()) }

    /**
     * VERIFICATION
     */
    factory { IsVerificationCompletedUseCase(get(), get(), get()) }
    factory { SetVerificationCompletedUseCase(get(), get()) }
    factory { FirebaseSendVerificationUseCase(get()) }
    factory { FirebaseCheckEmailVerificationUseCase(get()) }

    /**
     * LOCATION
     */
    factory { GetCitiesUseCase(get()) }
    factory { GetCountriesUseCase(get()) }
    factory { SetCityUseCase(get(), get()) }
    factory { SetCountryUseCase(get(), get()) }
    factory { IsCitySetUseCase(get(), get(), get()) }
    factory { IsCountrySetUseCase(get(), get(), get()) }
}