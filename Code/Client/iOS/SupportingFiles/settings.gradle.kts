import java.io.File


apply( from = "../../client-shared.settings.gradle.kts" )

rootProject.name = "client-ios"

include(":example")
project(":example").projectDir = file("../Example")

