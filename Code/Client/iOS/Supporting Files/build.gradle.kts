
buildscript {

    // Hack to get common properties read at this point,
    // since Gradle (Kotlin DSL) has no way to include a function.
    run {
        java.util.Properties().apply {
            File("$rootDir/common.properties").inputStream().use { fis ->
                load(fis)
            }
        }.forEach {
            extra[it.key.toString()] = it.value
        }
    }

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = extra["kotlin_version"].toString()))
        classpath("org.jetbrains.kotlin:kotlin-serialization:${extra["kotlin_version"].toString()}")
    }
}

allprojects {

    apply {
        from("${rootProject.projectDir}/common.gradle.kts")
    }

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }
}
