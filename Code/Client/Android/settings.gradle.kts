import java.io.File

// Workaround for: https://youtrack.jetbrains.com/issue/KT-27612
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
            }
        }
    }
}

rootProject.name = "client-android"

// Use Gradle Metadata to resolve MPP artifacts: https://github.com/gradle/gradle/blob/master/subprojects/docs/src/docs/design/gradle-module-metadata-1.0-specification.md
enableFeaturePreview("GRADLE_METADATA")

include(":mobile",":tv",":shared",":client-common",":android-client-common")

// The multi-platform project shared between Client and Server
project(":shared").projectDir = File(settingsDir, "../../Shared")

// The multi-platform project shared across all Clients
project(":client-common").projectDir = File(settingsDir, "../Common")

// The Android library shared between Android clients
project(":android-client-common").projectDir = File(settingsDir, "./common")
