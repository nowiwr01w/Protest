package com.nowiwr01p.meetings.di

import com.nowiwr01p.core_ui.navigators.MeetingsNavigator
import com.nowiwr01p.domain.meetings.usecase.GetMeetingsScreenCacheUseCase
import com.nowiwr01p.domain.meetings.usecase.SaveMeetingsScreenCacheUseCase
import com.nowiwr01p.domain.meetings.usecase.data.MeetingsScreenCache
import com.nowiwr01p.meetings.navigation.MeetingsNavigatorImpl
import com.nowiwr01p.meetings.ui.MeetingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleMeetings = module {
    single<MeetingsNavigator> {
        MeetingsNavigatorImpl()
    }

    viewModel {
        val scope = getKoin().getOrCreateScope(meetingsScreenScopeId, named(meetingsScreenScopeName))

        MeetingsViewModel(
            get(),
            get(),
            scope.get(),
            scope.get()
        )
    }

    scope(named(meetingsScreenScopeName)) {
        scoped { MeetingsScreenCache() }
        scoped { GetMeetingsScreenCacheUseCase(get()) }
        scoped { SaveMeetingsScreenCacheUseCase(get()) }
    }
}

private const val meetingsScreenScopeId = "meetingsScreenScopeId"
private const val meetingsScreenScopeName = "meetingsScreenScopeName"