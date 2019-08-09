import java.util.*
import java.util.Locale

/**
 * This script is applied by the build-scripts of every sub-project in the Multi-Platform template project.
 * It provides these centralized functions:
 * - Dependency versions; where one file 'common.properties' sets dependency versions for the entire project.
 * - Operating System determination; whether the build is occurring on Linux/Mac/Windows.
 */

/**
 * Determine the current operating system - used by jpackage task
 */
val os : String = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH)
extra["currentOs"] = when {
    ((os.indexOf("mac") >= 0) || (os.indexOf("darwin") >= 0)) -> "osx"
    (os.indexOf("win") >= 0) -> "windows"
    (os.indexOf("nux") >= 0) -> "linux"
    else -> throw Exception("Unsupported operating system: '$os'")
}

/**
 * Load entries from 'common.properties' into the context Gradle project's 'extra' properties.
 *
 * This is used to keep Kotlin language, Ktor and other library dependencies consistent across
 * several Multi-platform sub-projects.
 *
 * Even when referencing dependencies centrally in this way, version numbers which are common to families
 * of dependencies e.g. multi-platform Ktor components, would require repetition and maintenance.
 *
 * So, to ease maintenance of dependencies referenced in 'common.properties', substitutions in the familiar
 * form '$token' are also resolved by this loading code, where 'token' may be the key of another value in the
 * 'common.properties' file.
 *
 * e.g.
 * myVersion = 1.2.0
 * myDependency = myGroup:myId:$myVersion
 *
 * ...gets loaded as:
 * myVersion = 1.2.0
 * myDependency = myGroup:myId:1.2.0
 */
fun loadSubstitutedPropertiesToExtra(fileName: String) {

    // Load the properties file without substitutions
    val properties = Properties().apply {
        File(fileName).inputStream().use { fis ->
            load(fis)
        }
    }

    // Define recursive substitution by $token
    fun getResolved(key: String) : String? {

        val substituteRegex = Regex("\\\$([A-Za-z_0-9]+)")

        fun substitute(value: String) : String {
            var resolvingValue : String = value
            do {
                val lastValue = resolvingValue
                resolvingValue = substituteRegex.replace(value) { result ->
                    val substitutionKey : String = result.groupValues[1]
                    getResolved(substitutionKey) ?: throw Exception("Couldn't find substitution for key '$substitutionKey'")
                }
            } while(lastValue != resolvingValue) // Iteratively resolve until the string stabilises
            return resolvingValue
        }

        val unsubbedValue : String = properties.get(key) as? String ?: return null
        return substitute(value = unsubbedValue)
    }

    // Copy resolved values into Gradle 'extra'
    val extraValues = properties.map { (key,_) ->
        val resolvedValue = getResolved(key as String)
        key.toString() to resolvedValue
    }.toMap()

    //val pad : Int = extraValues.keys.map { it.length }.max() ?: 0

    extraValues.entries
        .sortedBy { (key,_)-> key }
        .forEach { (key, value) ->
            //println("${key.padEnd(pad)} = $value")
            extra[key] = value
        }
}

loadSubstitutedPropertiesToExtra(fileName = "$rootDir/common.properties")

// Define project dependencies by conventional module path

extra["androidClientCommonProject"] = { project(":android-client-common") } //as ()->ProjectDependency
extra["clientCommonProject"]        = { project(":client-common") }
extra["sharedProject"]              = { project(":shared") }

val isIosArm64 : Boolean = when(System.getenv("PLATFORM_PREFERRED_ARCH")) {
    "arm64" -> { println("* Compiling Kotlin for iOS ARM64 by environment *"); true }
    "X64"   -> { println("* Compiling Kotlin for iOS X64 by environment *"); false }
    else    -> { println("* Compiling Kotlin for iOS X64 by default *"); false }
}

extra["iosTargetName"] = if(isIosArm64) "iosArm64" else "iosX64"


