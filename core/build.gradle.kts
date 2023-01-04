import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("libs.common")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
}

val properties = gradleLocalProperties(rootDir)

val key: String = properties.getProperty("LOCATION_API_KEY")
val keyName: String = properties.getProperty("LOCATION_API_KEY_NAME")
val host: String = properties.getProperty("LOCATION_API_HOST")
val hostName: String = properties.getProperty("LOCATION_API_HOST_NAME")
val baseUrl: String = properties.getProperty("LOCATION_API_BASE_URL")

val authName: String = properties.getProperty("AUTH_WARNING_STORE_NAME")
val locationName: String = properties.getProperty("LOCATION_STORE_NAME")
val verificationName: String = properties.getProperty("VERIFICATION_STORE_NAME")

android {
    namespace = "com.nowiwr01p.core"

    buildTypes {
        debug {
            buildConfigField("String","LOCATION_API_KEY", key)
            buildConfigField("String","LOCATION_API_KEY_NAME", keyName)
            buildConfigField("String","LOCATION_API_HOST", host)
            buildConfigField("String","LOCATION_API_HOST_NAME", hostName)
            buildConfigField("String","LOCATION_API_BASE_URL", baseUrl)
            buildConfigField("String","LOCATION_DATA_STORE", locationName)
            buildConfigField("String","VERIFICATION_DATA_STORE", verificationName)
            buildConfigField("String", "AUTH_SECURITY_DATA_STORE", authName)
        }
        release {
            buildConfigField("String","LOCATION_API_KEY", key)
            buildConfigField("String","LOCATION_API_KEY_NAME", keyName)
            buildConfigField("String","LOCATION_API_HOST", host)
            buildConfigField("String","LOCATION_API_HOST_NAME", hostName)
            buildConfigField("String","LOCATION_API_BASE_URL", baseUrl)
            buildConfigField("String","LOCATION_DATA_STORE", locationName)
            buildConfigField("String","VERIFICATION_DATA_STORE", verificationName)
            buildConfigField("String", "AUTH_SECURITY_DATA_STORE", authName)
        }
    }
}