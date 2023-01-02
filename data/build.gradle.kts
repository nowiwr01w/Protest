plugins {
    id("libs.common")
}

android {
    namespace = "com.nowiwr01p.data"
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))
}