package com.nowiwr01p.meetings.di

import com.nowiwr01p.core_ui.navigators.MeetingsNavigator
import com.nowiwr01p.meetings.navigation.MeetingsNavigatorImpl
import com.nowiwr01p.meetings.ui.MeetingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMeetings = module {
    single<MeetingsNavigator> {
        MeetingsNavigatorImpl()
    }

    viewModel { MeetingsViewModel(get(), get()) }
}