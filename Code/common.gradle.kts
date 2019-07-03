import java.util.*

// Load entries from 'common.properties' into the context project's 'extra' properties.
// This is used to keep Kotlin language and Core library versions the same across
// all of the sub-projects.

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

fun Properties.getSubstituted(key: String) : String? {
    val unsubbedValue : String = get(key) as? String ?: return null
    return substitute(value = unsubbedValue)
}

fun loadSubstitutedPropertiesToExtra(fileName: String) {
    val properties = java.util.Properties().apply {
        File(fileName).inputStream().use { fis ->
            load(fis)
        }
    }
    println(properties.size)
    properties.forEach { (key,value) ->
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
//project.extra["extString"] = { key:String ->
//    project.extra[key] as? String ?: throw Exception("Unknown or non-String key '$key'")
//}

