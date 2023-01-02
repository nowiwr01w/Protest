plugins {
    id("libs.ui")
}

android {
    namespace = "com.nowiwr01p.map"
}

dependencies {
    implementation(project(mapOf("path" to ":core-ui")))
}