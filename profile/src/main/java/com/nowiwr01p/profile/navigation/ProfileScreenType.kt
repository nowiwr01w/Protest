package com.nowiwr01p.profile.navigation

import com.nowiwr01p.core_ui.Keys.ARG_PROFILE_EDIT_MODE

enum class ProfileScreenType(val route: String) {
    ProfileMainScreen("profile_main_screen?editMode={${ARG_PROFILE_EDIT_MODE}}")
}