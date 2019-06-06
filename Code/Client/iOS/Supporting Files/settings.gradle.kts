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

rootProject.name = file("..").name

// Use Gradle Metadata to resolve MPP artifacts: https://github.com/gradle/gradle/blob/master/subprojects/docs/src/docs/design/gradle-module-metadata-1.0-specification.md
enableFeaturePreview("GRADLE_METADATA")

include(":example",":shared",":client-common")

project(":example").projectDir = file("../Example")
project(":shared").projectDir = File(settingsDir, "../../../Shared")
project(":client-common").projectDir = File(settingsDir, "../../Common")

