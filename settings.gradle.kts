pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.kotlin.android") version "1.9.22"
        id("org.jetbrains.kotlin.kapt") version "1.9.22"
        id("com.android.application") version "8.4.2"
        id("com.google.dagger.hilt.android") version "2.51.1"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "stealthguard-root"
include(":android_frontend_app:app")
