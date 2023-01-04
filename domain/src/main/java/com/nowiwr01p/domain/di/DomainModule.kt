package com.nowiwr01p.domain.di

import com.nowiwr01p.domain.auth.usecase.*
import com.nowiwr01p.domain.location.usecase.GetCitiesUseCase
import com.nowiwr01p.domain.location.usecase.GetCountriesUseCase
import com.nowiwr01p.domain.location.usecase.SetCityUseCase
import com.nowiwr01p.domain.location.usecase.SetCountryUseCase
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
    factory { FirebaseSendVerificationUseCase(get()) }
    factory { FirebaseCheckEmailVerificationUseCase(get()) }

    /**
     * LOCATION
     */
    factory { GetCitiesUseCase(get()) }
    factory { GetCountriesUseCase(get()) }
    factory { SetCityUseCase(get()) }
    factory { SetCountryUseCase(get()) }
}