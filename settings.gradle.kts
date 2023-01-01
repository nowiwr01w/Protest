pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
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
include(":build-src")
include(":build-src")
