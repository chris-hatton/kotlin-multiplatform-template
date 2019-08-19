import java.io.File

apply( from = "common.settings.gradle.kts" )

rootProject.name = "client"

// Use Gradle Metadata to resolve MPP artifacts: https://github.com/gradle/gradle/blob/master/subprojects/docs/src/docs/design/gradle-module-metadata-1.0-specification.md
enableFeaturePreview("GRADLE_METADATA")

include(":shared",":client-shared",":multi-mvp")

project(":shared").projectDir = File(settingsDir, "../../Shared")

project(":client-shared").projectDir = File(settingsDir, "../Shared")
project(":multi-mvp").projectDir = File(settingsDir, "../../Lib/multi-mvp")
