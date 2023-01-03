package com.nowiwr01p.domain.di

import com.nowiwr01p.domain.auth.usecase.GetAuthSecurityWarningUseCase
import com.nowiwr01p.domain.auth.usecase.SetAuthSecurityWarningShownUseCase
import com.nowiwr01p.domain.auth.usecase.ValidateAuthDataUseCase
import org.koin.dsl.module

val moduleDomain = module  {

    /**
     * AUTH
     */
    factory { ValidateAuthDataUseCase(get()) }
    factory { GetAuthSecurityWarningUseCase(get()) }
    factory { SetAuthSecurityWarningShownUseCase(get()) }
}