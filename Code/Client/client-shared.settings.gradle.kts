
apply( from = "../shared.settings.gradle.kts" )

include(":client-shared")
project(":client-shared").projectDir = File(buildscript.sourceFile!!.parent,"Shared")

val libPath = buildscript.sourceFile!!.parent + "/../Lib"

include(":multi-mvp")
project(":multi-mvp").projectDir = File(libPath,"multi-mvp")

include(":coroutines-ui")
project(":coroutines-ui").projectDir = File(libPath,"coroutines-ui")
