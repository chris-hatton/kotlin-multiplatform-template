import java.io.File

// Workaround for: https://youtrack.jetbrains.com/issue/KT-27612
pluginManagement {

    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
            }
            if (requested.id.id == "com.android.library") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
}

rootProject.name = file("..").name

// Use Gradle Metadata to resolve MPP artifacts: https://github.com/gradle/gradle/blob/master/subprojects/docs/src/docs/design/gradle-module-metadata-1.0-specification.md
enableFeaturePreview("GRADLE_METADATA")

include(":example",":shared",":client-shared",":multi-mvp")

project(":example").projectDir = file("../Example")
project(":shared").projectDir = File(settingsDir, "../../../Shared")
project(":client-shared").projectDir = File(settingsDir, "../../Shared")
project(":multi-mvp").projectDir = File(settingsDir, "../../../Lib/multi-mvp")

