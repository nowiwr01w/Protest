package com.nowiwr01p.data.auth.di

import com.nowiwr01p.data.auth.repository.ValidateAuthDataRepositoryImpl
import com.nowiwr01p.data.auth.validators.EmailValidatorImpl
import com.nowiwr01p.data.auth.validators.PasswordValidatorImpl
import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.AppDispatchersImpl
import com.nowiwr01p.domain.auth.repository.ValidateAuthDataRepository
import com.nowiwr01p.domain.auth.usecase.ValidateAuthDataUseCase
import com.nowiwr01p.domain.auth.validators.EmailValidator
import com.nowiwr01p.domain.auth.validators.PasswordValidator
import org.koin.dsl.module

val moduleAuthData = module {
    single<AppDispatchers> {
        AppDispatchersImpl()
    }

    factory<EmailValidator> {
        EmailValidatorImpl()
    }
    factory<PasswordValidator> {
        PasswordValidatorImpl()
    }
    factory<ValidateAuthDataRepository> {
        ValidateAuthDataRepositoryImpl(get(), get(), get())
    }
    factory {
        ValidateAuthDataUseCase(get())
    }
}