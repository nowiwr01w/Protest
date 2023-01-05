package com.nowiwr01p.map.navigation

import com.nowiwr01p.core_ui.Keys

enum class MapScreenType(val route: String) {
    MapMainScreen("map_main_screen?coordinates={${Keys.ARG_CITY_LOCATION}}")
}