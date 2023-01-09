plugins {
    id("libs.ui")
}

android {
    namespace = "com.nowiwr01p.core_ui"
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
}