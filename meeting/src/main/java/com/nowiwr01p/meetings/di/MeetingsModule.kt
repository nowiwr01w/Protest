package com.nowiwr01p.meetings.di

import com.nowiwr01p.core_ui.navigators.MeetingsNavigator
import com.nowiwr01p.domain.meetings.create_meeting.usecase.GetCachedCategoriesUseCase
import com.nowiwr01p.domain.meetings.main.usecase.GetMeetingsScreenCacheUseCase
import com.nowiwr01p.domain.meetings.main.usecase.SaveMeetingsScreenCacheUseCase
import com.nowiwr01p.domain.meetings.main.usecase.data.MeetingsScreenCache
import com.nowiwr01p.domain.meetingsScreenScopeId
import com.nowiwr01p.domain.meetingsScreenScopeName
import com.nowiwr01p.meetings.navigation.MeetingsNavigatorImpl
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingMapper
import com.nowiwr01p.meetings.ui.create_meeting.CreateMeetingVewModel
import com.nowiwr01p.meetings.ui.main.MeetingsMapper
import com.nowiwr01p.meetings.ui.main.MeetingsViewModel
import com.nowiwr01p.meetings.ui.create_meeting.map.CreateMeetingMapViewModel
import com.nowiwr01p.meetings.ui.map_current_meeting.MapCurrentMeetingViewModel
import com.nowiwr01p.meetings.ui.meeting.MeetingViewModel
import com.nowiwr01p.meetings.ui.previews.MeetingsPreviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moduleMeetings = module {
    single<MeetingsNavigator> {
        MeetingsNavigatorImpl()
    }

    /**
     * MAIN SCREEN - MEETINGS
     */
    viewModel {
        val scope = getKoin().getOrCreateScope(meetingsScreenScopeId, named(meetingsScreenScopeName))

        MeetingsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            scope.get(),
            scope.get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    factory { MeetingsMapper() }

    scope(named(meetingsScreenScopeName)) {
        scoped { MeetingsScreenCache() }
        scoped { GetMeetingsScreenCacheUseCase(get()) }
        scoped { SaveMeetingsScreenCacheUseCase(get()) }
        scoped { GetCachedCategoriesUseCase(get()) }
    }

    /**
     * MAP CURRENT MEETING
     */
    viewModel {
        MapCurrentMeetingViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    /**
     * MEETINGS PREVIEWS
     */
    viewModel {
        MeetingsPreviewViewModel(get())
    }

    /**
     * CREATE MEETING MAP (START LOCATION + DRAW PATH)
     */
    viewModel {
        CreateMeetingMapViewModel(get())
    }

    /**
     * CREATE MEETING
     */
    viewModel {
        val scope = getKoin().getScope(meetingsScreenScopeId)

        CreateMeetingVewModel(
            get(),
            scope.get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    factory { CreateMeetingMapper() }

    /**
     * MEETING INFO
     */
    viewModel {
        MeetingViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}