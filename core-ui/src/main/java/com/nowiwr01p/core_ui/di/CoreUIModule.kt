package com.nowiwr01p.core_ui.di

import com.nowiwr01p.core_ui.ui.bottom_sheet.ShowBottomSheetHelper
import com.nowiwr01p.core_ui.ui.open_ilnks.OpenLinksHelper
import com.nowiwr01p.core_ui.ui.snack_bar.ShowSnackBarHelper
import com.nowiwr01p.core_ui.ui.status_bar.StatusBarColorHelper
import org.koin.dsl.module

val moduleCoreUI = module {
    single { OpenLinksHelper() }
    single { StatusBarColorHelper() }
    single { ShowBottomSheetHelper() }
    single { ShowSnackBarHelper(get()) }
}