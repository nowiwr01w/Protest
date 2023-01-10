package com.nowiwr01p.meetings.di

import com.nowiwr01p.core_ui.navigators.MeetingsNavigator
import com.nowiwr01p.domain.meetings.usecase.GetMeetingsScreenCacheUseCase
import com.nowiwr01p.domain.meetings.usecase.SaveMeetingsScreenCacheUseCase
import com.nowiwr01p.domain.meetings.usecase.data.MeetingsScreenCache
import com.nowiwr01p.meetings.navigation.MeetingsNavigatorImpl
import com.nowiwr01p.meetings.ui.create.CreateMeetingVewModel
import com.nowiwr01p.meetings.ui.main.MeetingsMapper
import com.nowiwr01p.meetings.ui.main.MeetingsViewModel
import com.nowiwr01p.meetings.ui.meeting.MeetingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleMeetings = module {
    single<MeetingsNavigator> {
        MeetingsNavigatorImpl()
    }

    /**
     * MAIN SCREEN
     */
    viewModel {
        val scope = getKoin().getOrCreateScope(meetingsScreenScopeId, named(meetingsScreenScopeName))

        MeetingsViewModel(
            get(),
            get(),
            get(),
            scope.get(),
            scope.get(),
            get()
        )
    }

    factory { MeetingsMapper() }

    scope(named(meetingsScreenScopeName)) {
        scoped { MeetingsScreenCache() }
        scoped { GetMeetingsScreenCacheUseCase(get()) }
        scoped { SaveMeetingsScreenCacheUseCase(get()) }
    }

    /**
     * CREATE MEETING
     */
    viewModel { CreateMeetingVewModel() }

    /**
     * MEETING SCREEN
     */
    viewModel { MeetingViewModel(get(), get()) }
}

private const val meetingsScreenScopeId = "meetingsScreenScopeId"
private const val meetingsScreenScopeName = "meetingsScreenScopeName"