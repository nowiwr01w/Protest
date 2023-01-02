plugins {
    id("libs.ui")
}

android {
    namespace = "com.nowiwr01p.profile"
}

dependencies {
    implementation(project(mapOf("path" to ":core-ui")))
}