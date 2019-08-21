import java.io.File


apply( from = "../../client-shared.settings.gradle.kts" )

rootProject.name = file("..").name

include(":example")
project(":example").projectDir = file("../Example")

