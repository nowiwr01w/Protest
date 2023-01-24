package com.nowiwr01p.core_ui.di

import com.nowiwr01p.core.BuildConfig
import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.ui.recaptcha.RecaptchaHelper
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.core_ui.ui.status_bar.StatusBarColorHelper
import com.nowiwr01p.domain.googleCloudApi
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleCoreUI = module {

    single { OpenLinksHelper() }
    single { StatusBarColorHelper() }
    single { ShowBottomSheetHelper() }
    single { ShowSnackBarHelper(get()) }

    single(named(googleCloudApi)) {
        BuildConfig.RECAPTCHA_API_KEY
    }

    single {
        val apiKey = get<String>(named(googleCloudApi))
        RecaptchaHelper(
            apiKey,
            androidApplication()
        )
    }
}