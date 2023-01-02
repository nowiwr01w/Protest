plugins {
    id("libs.ui")
}

android {
    namespace = "com.nowiwr01p.auth"
}

dependencies {
    implementation(project(mapOf("path" to ":core-ui")))
}