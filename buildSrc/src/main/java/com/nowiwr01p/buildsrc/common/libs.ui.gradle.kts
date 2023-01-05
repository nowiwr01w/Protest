import com.nowiwr01p.buildsrc.extensions.accompanistDependencies
import com.nowiwr01p.buildsrc.extensions.animationDependencies
import com.nowiwr01p.buildsrc.extensions.biometricDependencies
import com.nowiwr01p.buildsrc.extensions.commonUiDependencies
import com.nowiwr01p.buildsrc.extensions.navigationDependencies
import com.nowiwr01p.buildsrc.extensions.mapDependencies
import org.gradle.kotlin.dsl.dependencies
import com.nowiwr01p.buildsrc.dependency.Basic.Version

plugins {
    id("libs.common")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Version.COMPOSE
    }
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))

    accompanistDependencies()
    animationDependencies()
    biometricDependencies()
//    roomDependencies()
    commonUiDependencies()
    navigationDependencies()
    mapDependencies()
}