
// Top-level build file where you can add configuration options common to all sub-projects/modules.

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
        classpath("com.android.tools.build:gradle:3.4.1")
        classpath(kotlin("gradle-plugin", version = extra["kotlin_version"].toString()))
        classpath("org.jetbrains.dokka:dokka-android-gradle-plugin:${extra["android_gradle_plugin_version"].toString()}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${extra["kotlin_version"].toString()}")
    }
}

allprojects {

    apply {
        from("$rootDir/common.gradle.kts")
    }

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}