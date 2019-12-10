/**
 * This Gradle Settings file is needed when opening the Desktop Client project in an IDE.
 */

import java.io.File

// Apply the settings which are shared across all Client platform projects.
apply( from = "../client-shared.settings.gradle.kts" )

rootProject.name = "client"

