package com.nowiwr01p.core_ui.di

import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import org.koin.dsl.module

val moduleCoreUI = module {
    single { ShowSnackBarHelper() }
    single { ShowBottomSheetHelper() }
}