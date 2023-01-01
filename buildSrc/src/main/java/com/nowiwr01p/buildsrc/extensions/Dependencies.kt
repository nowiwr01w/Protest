package com.nowiwr01p.buildsrc.extensions

import com.nowiwr01p.buildsrc.dependency.*
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Dependencies
 */
fun DependencyHandler.animationDependencies() {
    implementation(Animations.LOTTIE)
}

fun DependencyHandler.biometricDependencies() {
    implementation(Biometric.BIOMETRIC_LIB)
}

fun DependencyHandler.koinDependencies() {
    implementation(Koin.KOIN_ANDROID)
    implementation(Koin.KOIN_COMPOSE_LIB)
}

fun DependencyHandler.navigationDependencies() {
    implementation(Navigation.NAVIGATION_COMPOSE)
    implementation(Navigation.NAVIGATION_COMMON)
}

fun DependencyHandler.roomDependencies() {
    implementation(Room.ROOM_KTX)
    implementation(Room.ROOM_RUNTIME)
    implementation(Room.ROOM_COMPILER)
}

fun DependencyHandler.networkDependencies() {
    implementation(Network.RETROFIT_LIB)
    implementation(Network.RETROFIT_SERIALIZATION_LIB)
    implementation(Network.OKHTTP_LIB)
    implementation(Network.OKHTTP_LOGGING)
    implementation(Network.KOTLIN_SERIALIZATION)
    implementation(Network.GSON_LIB)
}

fun DependencyHandler.commonUiDependencies() {
    implementation(Basic.KOTLIN_CORE_KTX)
    implementation(Basic.LIFECYCLE_LIB)
    implementation(Basic.UI)
    implementation(Basic.MATERIAL)
    implementation(Basic.MATERIAL_ICONS_EXTENDED)
    implementation(Basic.TOOLING)
    implementation(Basic.TOOLING_PREVIEW)
    implementation(Basic.CONSTRAINT_LAYOUT)
    implementation(Basic.TIMBER_LIB)
    implementation(Basic.ACTIVITY_COMPOSE)
}

fun DependencyHandler.accompanistDependencies() {
    implementation(Accompanist.ACCOMPANIST_PAGER)
    implementation(Accompanist.ACCOMPANIST_INSETS)
    implementation(Accompanist.ACCOMPANIST_FLOW_ROW)
    implementation(Accompanist.ACCOMPANIST_SYSTEM_UI)
    implementation(Accompanist.ACCOMPANIST_SWIPE_REFRESH)
    implementation(Accompanist.MATERIAL_ACCOMPANIST_PLACEHOLDER)
}

fun DependencyHandler.unitTestDependencies() {
    testImplementation(UnitTest.JUNIT_LIB)
    testImplementation(UnitTest.JUNIT_JB)
    testImplementation(UnitTest.JUNIT_RUNTIME)
    testImplementation(UnitTest.JUNIT_PARAMETRIZED)
    testImplementation(UnitTest.MOCKITO_LIB)
    testImplementation(UnitTest.MOCKITO_CORE)
    testImplementation(UnitTest.MOCKITO_INLINE_LIB)
    testImplementation(UnitTest.MOCK_WEB_SERVER_LIB)
    testImplementation(UnitTest.COROUTINES_LIB)
}