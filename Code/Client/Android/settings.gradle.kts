import java.io.File

apply( from = "common.settings.gradle.kts" )

rootProject.name = "client-android"

// Use Gradle Metadata to resolve MPP artifacts: https://github.com/gradle/gradle/blob/master/subprojects/docs/src/docs/design/gradle-module-metadata-1.0-specification.md
enableFeaturePreview("GRADLE_METADATA")

include(":mobile",":tv",":shared",":client-shared",":android-client-shared",":multi-mvp")

// The multi-platform project shared between Client and Server
project(":shared").projectDir = File(settingsDir, "../../Shared")

// The multi-platform project shared across all Clients
project(":client-shared").projectDir = File(settingsDir, "../Shared")

// The Android library shared between Android clients
project(":android-client-shared").projectDir = File(settingsDir, "./android-client-shared")

// The multi-platform project shared across all Clients
project(":multi-mvp").projectDir = File(settingsDir, "../../Lib/multi-mvp")

