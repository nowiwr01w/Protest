plugins {
    id("libs.ui")
}

android {
    namespace = "com.nowiwr01p.navigation"
}

dependencies {
    implementation(project(mapOf("path" to ":core-ui")))
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":auth")))
    implementation(project(mapOf("path" to ":meetings")))
    implementation(project(mapOf("path" to ":news")))
    implementation(project(mapOf("path" to ":profile")))
    implementation(project(mapOf("path" to ":data")))
}