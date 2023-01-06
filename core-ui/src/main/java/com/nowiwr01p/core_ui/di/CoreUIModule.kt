package com.nowiwr01p.core_ui.di

import com.nowiwr01p.core_ui.bottom_sheet.ShowBottomSheetHelper
import org.koin.dsl.module

val moduleCoreUI = module {
    single { ShowBottomSheetHelper() }
}