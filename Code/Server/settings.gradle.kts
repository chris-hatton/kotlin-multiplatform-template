
// Allow Gradle to follow meta-data to resolve multi-platform artifacts
enableFeaturePreview("GRADLE_METADATA")

apply( from = "common.settings.gradle.kts" )

rootProject.name = "Server"

include(":shared")

val sharedProject = project(":shared")
sharedProject.projectDir = File(settingsDir, "../Shared")

