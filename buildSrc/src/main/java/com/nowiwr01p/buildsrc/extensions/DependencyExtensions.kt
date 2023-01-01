package com.nowiwr01p.buildsrc.extensions

import com.nowiwr01p.buildsrc.dependency.Accompanist
import com.nowiwr01p.buildsrc.dependency.Animations
import com.nowiwr01p.buildsrc.dependency.Basic
import com.nowiwr01p.buildsrc.dependency.Koin
import com.nowiwr01p.buildsrc.dependency.Biometric
import com.nowiwr01p.buildsrc.dependency.Network
import com.nowiwr01p.buildsrc.dependency.Room
import com.nowiwr01p.buildsrc.dependency.UnitTest
import com.nowiwr01p.buildsrc.dependency.Navigation
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Adds a dependency to the `debugImplementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.debugImplementation(dependencyNotation: String): Dependency? {
    return add("debugImplementation", dependencyNotation)
}

/**
 * Adds a dependency to the `implementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.implementation(dependencyNotation: String): Dependency? {
    return add("implementation", dependencyNotation)
}

/**
 * Adds a dependency to the `api` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.api(dependencyNotation: String): Dependency? {
    return add("api", dependencyNotation)
}

/**
 * Adds a dependency to the `kapt` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.kapt(dependencyNotation: String): Dependency? {
    return add("kapt", dependencyNotation)
}

/**
 * Adds a dependency to the `testImplementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.testImplementation(dependencyNotation: String): Dependency? {
    return add("testImplementation", dependencyNotation)
}


/**
 * Adds a dependency to the `androidTestImplementation` configuration.
 *
 * @param dependencyNotation name of dependency to add at specific configuration
 *
 * @return the dependency
 */
fun DependencyHandler.androidTestImplementation(dependencyNotation: String): Dependency? {
    return add("androidTestImplementation", dependencyNotation)
}

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
    implementation(Koin.KOIN_LIB)
    implementation(Koin.KOIN_COMPOSE)
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
    implementation(Basic.TIMBER_LIB)
}

fun DependencyHandler.commonUiDependencies() {
    implementation(Basic.KOTLIN_CORE_KTX)
    implementation(Basic.UI)
    implementation(Basic.MATERIAL)
    implementation(Basic.MATERIAL_ICONS_EXTENDED)
    implementation(Basic.TOOLING)
    implementation(Basic.TOOLING_PREVIEW)
    implementation(Basic.CONSTRAINT_LAYOUT)
    implementation(Basic.TIMBER_LIB)
}

fun DependencyHandler.accompanistDependencies() {
    implementation(Accompanist.ACCOMPANIST_PAGER)
    implementation(Accompanist.ACCOMPANIST_INSETS)
    implementation(Accompanist.ACCOMPANIST_FLOW_ROW)
    implementation(Accompanist.ACCOMPANIST_SYSTEM_UI)
    implementation(Accompanist.ACCOMPANIST_SWIPE_REFRESH)
    implementation(Accompanist.ACCOMPANIST_NAV_ANIMATION)
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