plugins {
    id("libs.ui")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
}

android {
    namespace = "com.nowiwr01p.news"
}

dependencies {
    implementation(project(mapOf("path" to ":core-ui")))
    implementation(project(mapOf("path" to ":core")))
}