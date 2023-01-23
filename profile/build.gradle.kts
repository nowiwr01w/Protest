import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("libs.ui")
}

android {
    namespace = "com.nowiwr01p.profile"
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":core-ui")))
}

val properties = gradleLocalProperties(rootDir)

val telegramLink: String = properties.getProperty("TELEGRAM_LINK")
val organizerLink: String = properties.getProperty("ORGANIZER_LINK")
val writerLink: String = properties.getProperty("WRITER_LINK")
val bugLink: String = properties.getProperty("BUG_LINK")
val suggestIdeaLink: String = properties.getProperty("SUGGEST_IDEA_LINK")
val developmentLink: String = properties.getProperty("DEVELOPMENT_LINK")
val supportProjectLink: String = properties.getProperty("SUPPORT_PROJECT_LINK")
val privacyLink: String = properties.getProperty("PRIVACY_LINK")
val termsLink: String = properties.getProperty("TERMS_LINK")

android {
    namespace = "com.nowiwr01p.profile"

    buildTypes {
        debug {
            buildConfigField("String","TELEGRAM_LINK", telegramLink)
            buildConfigField("String","ORGANIZER_LINK", organizerLink)
            buildConfigField("String","WRITER_LINK", writerLink)
            buildConfigField("String","BUG_LINK", bugLink)
            buildConfigField("String","SUGGEST_IDEA_LINK", suggestIdeaLink)
            buildConfigField("String","DEVELOPMENT_LINK", developmentLink)
            buildConfigField("String","SUPPORT_PROJECT_LINK", supportProjectLink)
            buildConfigField("String","PRIVACY_LINK", privacyLink)
            buildConfigField("String","TERMS_LINK", termsLink)
        }
        release {
            buildConfigField("String","TELEGRAM_LINK", telegramLink)
            buildConfigField("String","ORGANIZER_LINK", organizerLink)
            buildConfigField("String","WRITER_LINK", writerLink)
            buildConfigField("String","BUG_LINK", bugLink)
            buildConfigField("String","SUGGEST_IDEA_LINK", suggestIdeaLink)
            buildConfigField("String","DEVELOPMENT_LINK", developmentLink)
            buildConfigField("String","SUPPORT_PROJECT_LINK", supportProjectLink)
            buildConfigField("String","PRIVACY_LINK", privacyLink)
            buildConfigField("String","TERMS_LINK", termsLink)
        }
    }
}