import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("libs.ui")
}

val properties = gradleLocalProperties(rootDir)

val privacyLink: String = properties.getProperty("PRIVACY_LINK")

android {
    namespace = "com.nowiwr01p.auth"

    buildTypes {
        debug {
            buildConfigField("String","PRIVACY_LINK", privacyLink)
        }
        release {
            buildConfigField("String","PRIVACY_LINK", privacyLink)
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":core-ui")))
}