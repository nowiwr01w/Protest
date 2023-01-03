package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Accompanist.Version.ACCOMPANIST

object Accompanist {

    const val PAGER = "com.google.accompanist:accompanist-pager:$ACCOMPANIST"
    const val INSETS = "com.google.accompanist:accompanist-insets-ui:$ACCOMPANIST"
    const val FLOW_ROW = "com.google.accompanist:accompanist-flowlayout:$ACCOMPANIST"
    const val SYSTEM_UI = "com.google.accompanist:accompanist-systemuicontroller:$ACCOMPANIST"
    const val SWIPE_REFRESH = "com.google.accompanist:accompanist-swiperefresh:$ACCOMPANIST"
    const val MATERIAL_PLACEHOLDER = "com.google.accompanist:accompanist-placeholder-material:$ACCOMPANIST"

    private object Version {
        const val ACCOMPANIST = "0.28.0"
    }
}