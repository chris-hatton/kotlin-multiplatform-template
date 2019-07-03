import java.util.*

/**
 * Load entries from 'common.properties' into the context Gradle project's 'extra' properties.
 * This is used to keep Kotlin language, Ktor and other library dependencies the same across
 * several Multi-platform sub-projects.
 *
 * Even when referencing dependencies centrally in this way, it was found that version numbers common to families
 * of dependencies e.g. multi-platform Ktor components, were repeated and needed to be maintained in sync.
 *
 * To further ease maintenance of the dependencies in 'common.properties', then, substitutions in the familiar
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

    val properties = Properties().apply {
        File(fileName).inputStream().use { fis ->
            load(fis)
        }
    }

    fun Properties.getSubstituted(key: String) : String? {

        val substituteRegex = Regex("\\\$([A-Za-z_0-9]+)")

        fun Properties.substitute(value: String) : String {
            var subbedValue : String = value
            do {
                val lastValue = subbedValue
                subbedValue = substituteRegex.replace(value) { result ->
                    val substitutionKey : String = result.groupValues[1]
                    this.getSubstituted(substitutionKey) ?: throw Exception()
                }
            } while(lastValue != subbedValue)
            return subbedValue
        }

        val unsubbedValue : String = get(key) as? String ?: return null
        return substitute(value = unsubbedValue)
    }

    properties.forEach { (key,_) ->
        val fullValue = properties.getSubstituted(key as String)
        println("$key = $fullValue")
        extra[key.toString()] = fullValue
    }
}

loadSubstitutedPropertiesToExtra(fileName = "$rootDir/common.properties")

// Define project dependencies by conventional module path

extra["androidClientCommonProject"] = { project(":android-client-common") } //as ()->ProjectDependency
extra["clientCommonProject"]        = { project(":client-common") }
extra["sharedProject"]              = { project(":shared") }
