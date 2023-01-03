pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Meetings"

include(":app")
include(":core-ui")
include(":core")
include(":auth")
include(":map")
include(":meetings")
include(":navigation")
include(":news")
include(":profile")
include(":domain")
include(":data")