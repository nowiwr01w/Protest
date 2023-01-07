plugins {
    id("libs.ui")
}

android {
    namespace = "com.nowiwr01p.news"
}

dependencies {
    implementation(project(mapOf("path" to ":core-ui")))
    implementation(project(mapOf("path" to ":core")))
}