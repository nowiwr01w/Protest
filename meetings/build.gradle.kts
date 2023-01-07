plugins {
    id("libs.ui")
}

android {
    namespace = "com.nowiwr01p.meetings"
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":core-ui")))
}