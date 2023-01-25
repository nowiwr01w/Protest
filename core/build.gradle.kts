import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("libs.common")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
}

val properties = gradleLocalProperties(rootDir)

val authName: String = properties.getProperty("AUTH_WARNING_STORE_NAME")
val locationName: String = properties.getProperty("LOCATION_STORE_NAME")
val verificationName: String = properties.getProperty("VERIFICATION_STORE_NAME")

android {
    namespace = "com.nowiwr01p.core"

    buildTypes {
        debug {
            buildConfigField("String", "AUTH_SECURITY_DATA_STORE", authName)
            buildConfigField("String","LOCATION_DATA_STORE", locationName)
            buildConfigField("String","VERIFICATION_DATA_STORE", verificationName)
        }
        release {
            buildConfigField("String", "AUTH_SECURITY_DATA_STORE", authName)
            buildConfigField("String","LOCATION_DATA_STORE", locationName)
            buildConfigField("String","VERIFICATION_DATA_STORE", verificationName)
        }
    }
}