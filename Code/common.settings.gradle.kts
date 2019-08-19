
buildCache {
    local<DirectoryBuildCache> {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

// Workaround for: https://youtrack.jetbrains.com/issue/KT-27612
pluginManagement {

    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }

    apply(from = "$rootDir/common.gradle.kts")

    val androidGradlePlugin       : String by extra
    val kotlinSerializationPlugin : String by extra
    val kotlinGradlePlugin        : String by extra

    /**
     * Having resolutionStrategy defined here allows plugin ID's nominated in the `plugins {}` block of each
     * Gradle sub-project to be mapped to actual Gradle artifacts just once.
     */
    resolutionStrategy {
        eachPlugin {
            val module = when(requested.id.id) {
                "org.jetbrains.kotlin.jvm",
                "kotlin-multiplatform"     -> kotlinGradlePlugin
                "kotlinx-serialization"    -> kotlinSerializationPlugin
                "com.android.library"      -> androidGradlePlugin
                else -> return@eachPlugin
            }
            useModule(module)
        }
    }
}