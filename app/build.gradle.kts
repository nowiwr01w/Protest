import com.nowiwr01p.buildsrc.dependency.BuildConfig
import com.nowiwr01p.buildsrc.extensions.*

plugins {
    id("kotlin-android")
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("/Users/nowiwr01/Desktop/meetings.jks")
            keyAlias = "2000PutinHuylo2022"
            keyPassword = "UnexpectedProtestsApp2022"
            storePassword = "UnexpectedProtestsApp2022"
        }
    }
    namespace = BuildConfig.APP_ID
    compileSdk = BuildConfig.COMPILE_SDK

    defaultConfig {
        applicationId = BuildConfig.APP_ID
        minSdk = BuildConfig.MIN_SDK
        targetSdk = BuildConfig.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("release")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildConfig.COMPOSE_VERSION
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":core-ui")))
    implementation(project(mapOf("path" to ":navigation")))

    animationDependencies()
    biometricDependencies()
    koinDependencies()
    firebaseDependencies()
    navigationDependencies()
    networkDependencies()
    commonUiDependencies()
    accompanistDependencies()
    unitTestDependencies()
}