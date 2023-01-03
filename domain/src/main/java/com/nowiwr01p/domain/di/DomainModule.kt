package com.nowiwr01p.domain.di

import com.nowiwr01p.domain.auth.usecase.*
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
}