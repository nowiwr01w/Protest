plugins {
    id("libs.common")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
}

android {
    namespace = "com.nowiwr01p.domain"
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
}