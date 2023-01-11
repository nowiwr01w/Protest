package com.nowiwr01p.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nowiwr01p.core_ui.bottom_navigation.BottomNavigationItem
import com.nowiwr01p.meetings.MeetingsScreen
import com.nowiwr01p.news.NewsScreen
import com.nowiwr01p.profile.ProfileScreen

enum class BottomNavigationItems(
    @StringRes override val titleId: Int,
    @DrawableRes override val iconId: Int,
    override val route: String
) : BottomNavigationItem {

    Meetings(
        titleId = R.string.meetings_title,
        iconId = R.drawable.ic_meetings,
        route = MeetingsScreen.MeetingsMainScreen.route
    ),
    News(
        titleId = R.string.news_title,
        iconId = R.drawable.ic_news,
        route = NewsScreen.NewsMainScreen.route
    ),
    Profile(
        titleId = R.string.profile_title,
        iconId = R.drawable.ic_profile,
        route = ProfileScreen.ProfileMainScreen.route
    )
}