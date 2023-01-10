package com.nowiwr01p.buildsrc.extensions

import com.nowiwr01p.buildsrc.dependency.*
import com.nowiwr01p.buildsrc.dependency.Map
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

fun DependencyHandler.dataStoreDependencies() {
    implementation(DataStore.DATASTORE)
}

fun DependencyHandler.firebaseDependencies() {
    implementation(Firebase.AUTH)
    implementation(Firebase.DATABASE)
}

fun DependencyHandler.koinDependencies() {
    implementation(Koin.ANDROID)
    implementation(Koin.COMPOSE)
}

fun DependencyHandler.navigationDependencies() {
    implementation(Navigation.COMPOSE)
    implementation(Navigation.COMMON)
}

fun DependencyHandler.roomDependencies() {
    implementation(Room.KTX)
    implementation(Room.RUNTIME)
    implementation(Room.COMPILER)
}

fun DependencyHandler.mapDependencies() {
    implementation(Map.GOOGLE_MAP)
    implementation(Map.PLAY_SERVICES)
    implementation(Map.COMPOSE_FOUNDATION)
    implementation(Map.GOOGLE_MAP_WIDGETS)
}

fun DependencyHandler.networkDependencies() {
    implementation(Network.RETROFIT_LIB)
    implementation(Network.RETROFIT_SERIALIZATION_LIB)
    implementation(Network.OKHTTP_LIB)
    implementation(Network.OKHTTP_LOGGING)
    implementation(Network.KOTLIN_SERIALIZATION)
    implementation(Network.GSON_LIB)
    implementation(Network.GSON_CONVERTER_LIB)
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
    implementation(Basic.COIL)
    implementation(Basic.CUSTOM_TABS)
}

fun DependencyHandler.accompanistDependencies() {
    implementation(Accompanist.PAGER)
    implementation(Accompanist.INSETS)
    implementation(Accompanist.FLOW_ROW)
    implementation(Accompanist.SYSTEM_UI)
    implementation(Accompanist.SWIPE_REFRESH)
    implementation(Accompanist.MATERIAL_PLACEHOLDER)
}

fun DependencyHandler.dateTimeDependencies() {
    implementation(Basic.JODA_TIME)
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