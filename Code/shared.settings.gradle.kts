
// Allow Gradle to follow meta-data to resolve multi-platform artifacts
enableFeaturePreview("GRADLE_METADATA")

//buildCache {
//    local<DirectoryBuildCache> {
//        directory = File(rootDir, "build-cache")
//        removeUnusedEntriesAfterDays = 30
//    }
//}

// Workaround for: https://youtrack.jetbrains.com/issue/KT-27612
pluginManagement {

    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }

    apply(from = "shared.gradle.kts")

    val androidGradlePlugin       : String by extra
    val kotlinSerializationPlugin : String by extra
    val kotlinGradlePlugin        : String by extra
    val javaFxPlugin              : String by extra
    val bintrayGradlePlugin       : String by extra

    /**
     * Having resolutionStrategy defined here allows plugin ID's nominated in the `plugins {}` block of each
     * Gradle sub-project to be mapped to actual Gradle artifacts just once.
     */
    resolutionStrategy {
        eachPlugin {
            val module = when(requested.id.id) {
                "org.jetbrains.kotlin.jvm",
                "org.jetbrains.kotlin.multiplatform",
                "kotlin-multiplatform"     -> kotlinGradlePlugin
                "kotlinx-serialization"    -> kotlinSerializationPlugin
                "com.android.library"      -> androidGradlePlugin
                "com.jfrog.bintray"        -> bintrayGradlePlugin
                else -> return@eachPlugin
            }
            useModule(module)
        }
    }
}

include(":shared")
project(":shared").projectDir = File(buildscript.sourceFile!!.parent,"Shared")