package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.BuildConfig.KOTLIN_VERSION
import com.nowiwr01p.buildsrc.dependency.UnitTest.Version.COROUTINES
import com.nowiwr01p.buildsrc.dependency.UnitTest.Version.JUNIT
import com.nowiwr01p.buildsrc.dependency.UnitTest.Version.MOCKITO
import com.nowiwr01p.buildsrc.dependency.UnitTest.Version.MOCKITO_INLINE
import com.nowiwr01p.buildsrc.dependency.UnitTest.Version.MOCK_WEB_SERVER

object UnitTest {

    const val JUNIT_LIB = "org.junit.jupiter:junit-jupiter-api:$JUNIT"
    const val JUNIT_RUNTIME = "org.junit.jupiter:junit-jupiter-engine:$JUNIT"
    const val JUNIT_PARAMETRIZED = "org.junit.jupiter:junit-jupiter-params:$JUNIT"
    const val JUNIT_JB = "org.jetbrains.kotlin:kotlin-test-junit:$KOTLIN_VERSION"

    const val MOCKITO_LIB = "org.mockito.kotlin:mockito-kotlin:$MOCKITO"
    const val MOCKITO_CORE = "org.mockito:mockito-core:${Version.MOCKITO_INLINE}"
    const val MOCKITO_INLINE_LIB = "org.mockito:mockito-inline:${MOCKITO_INLINE}"

    const val COROUTINES_LIB = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES"

    const val MOCK_WEB_SERVER_LIB = "com.squareup.okhttp3:mockwebserver:$MOCK_WEB_SERVER"

    private object Version {
        const val MOCKITO = "4.0.0"
        const val MOCKITO_INLINE = "4.8.1"
        const val JUNIT = "5.8.2"
        const val COROUTINES = "1.6.4"
        const val MOCK_WEB_SERVER = "4.10.0"
    }
}