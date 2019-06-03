
// Allow Gradle to follow meta-data to resolve multi-platform artifacts
enableFeaturePreview("GRADLE_METADATA")

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

rootProject.name = "Server"

include(":shared")

val sharedProject = project(":shared")
sharedProject.projectDir = File(settingsDir, "../Shared")

