package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Basic.Version.COIL_COMPOSE_VERSION
import com.nowiwr01p.buildsrc.dependency.Basic.Version.COMPOSE
import com.nowiwr01p.buildsrc.dependency.Basic.Version.COMPOSE_ACTIVITY
import com.nowiwr01p.buildsrc.dependency.Basic.Version.CONSTRAINT
import com.nowiwr01p.buildsrc.dependency.Basic.Version.COIL_VERSION
import com.nowiwr01p.buildsrc.dependency.Basic.Version.CUSTOM_TABS_VERSION
import com.nowiwr01p.buildsrc.dependency.Basic.Version.MATERIAL_DIALOG
import com.nowiwr01p.buildsrc.dependency.Basic.Version.JODA
import com.nowiwr01p.buildsrc.dependency.Basic.Version.KOTLIN_KTX
import com.nowiwr01p.buildsrc.dependency.Basic.Version.LIFECYCLE
import com.nowiwr01p.buildsrc.dependency.Basic.Version.TIMBER

object Basic {

    const val KOTLIN_CORE_KTX = "androidx.core:core-ktx:$KOTLIN_KTX"
    const val LIFECYCLE_LIB = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE"

    const val UI = "androidx.compose.ui:ui:$COMPOSE"
    const val MATERIAL = "androidx.compose.material:material:$COMPOSE"
    const val MATERIAL_ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:$COMPOSE"
    const val TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE"
    const val TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$COMPOSE"

    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:$COMPOSE_ACTIVITY"

    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout-compose:$CONSTRAINT"

    const val COIL = "com.github.skydoves:landscapist-coil:$COIL_VERSION"
    const val COIL_COMPOSE = "io.coil-kt:coil-compose:$COIL_COMPOSE_VERSION"

    const val TIMBER_LIB = "com.jakewharton.timber:timber:$TIMBER"

    const val JODA_TIME = "joda-time:joda-time:$JODA"

    const val CUSTOM_TABS = "androidx.browser:browser:$CUSTOM_TABS_VERSION"

    const val ALERT_DIALOG = "io.github.vanpra.compose-material-dialogs:core:$MATERIAL_DIALOG"
    const val DATE_TIME_DIALOGS = "io.github.vanpra.compose-material-dialogs:datetime:$MATERIAL_DIALOG"

    object Version {
        const val COMPOSE = "1.4.0-alpha02"
        const val CONSTRAINT = "1.1.0-alpha01"
        const val KOTLIN_KTX = "1.9.0"
        const val TIMBER = "5.0.1"
        const val LIFECYCLE = "2.5.1"
        const val COMPOSE_ACTIVITY = "1.7.0-alpha02"
        const val COIL_VERSION = "2.1.0"
        const val COIL_COMPOSE_VERSION = "2.1.0"
        const val JODA = "2.12.2"
        const val CUSTOM_TABS_VERSION = "1.4.0"
        const val MATERIAL_DIALOG = "0.9.0"
    }
}