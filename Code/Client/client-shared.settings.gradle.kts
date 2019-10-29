
apply( from = "../shared.settings.gradle.kts" )

include(":client-shared")
project(":client-shared").projectDir = File(buildscript.sourceFile!!.parent,"Shared")
