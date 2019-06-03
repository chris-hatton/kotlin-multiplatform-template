
// Load entries from 'common.properties' into the context project's 'extra' properties.
// This is used to keep Kotlin language and Core library versions the same across
// all the sub-projects.

java.util.Properties().apply {
    File("$rootDir/common.properties").inputStream().use { fis ->
        load(fis)
    }
}.forEach {
    project.extra[it.key.toString()] = it.value
}
