package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Basic.Version.COMPOSE
import com.nowiwr01p.buildsrc.dependency.Basic.Version.CONSTRAINT
import com.nowiwr01p.buildsrc.dependency.Basic.Version.KOTLIN_KTX
import com.nowiwr01p.buildsrc.dependency.Basic.Version.TIMBER

object Basic {

    const val KOTLIN_CORE_KTX = "androidx.core:core-ktx:$KOTLIN_KTX"

    const val UI = "androidx.compose.ui:ui:$COMPOSE"
    const val MATERIAL = "androidx.compose.material:material:$COMPOSE"
    const val MATERIAL_ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:$COMPOSE"

    const val TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE"
    const val TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$COMPOSE"

    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout-compose:$CONSTRAINT"

    const val TIMBER_LIB = "com.jakewharton.timber:timber:$TIMBER"

    object Version {
        const val COMPOSE = "1.2.0-alpha08"
        const val CONSTRAINT = "1.1.0"
        const val KOTLIN_KTX = "1.9.0"
        const val TIMBER = "5.0.1"
    }
}