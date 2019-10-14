import java.io.File

apply( from = "../client-shared.settings.gradle.kts" )

rootProject.name = "client-android"

include(":mobile",":tv",":android-client-shared")

// The Android library shared between Android clients
project(":android-client-shared").projectDir = File(settingsDir, "./android-client-shared")
