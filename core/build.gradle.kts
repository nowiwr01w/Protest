plugins {
    id("libs.ui")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

android {
    namespace = "com.nowiwr01p.core"

    buildTypes {
        debug {
            buildConfigField(
                type = "String",
                name = "AUTH_SECURITY_DATA_STORE",
                value = "\"auth_security.pb\""
            )
        }
        release {
            buildConfigField(
                type = "String",
                name = "AUTH_SECURITY_DATA_STORE",
                value = "\"auth_security.pb\""
            )
        }
    }
}