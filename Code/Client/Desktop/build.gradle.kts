
buildscript {

    val kotlin_version                : String by project
    val android_gradle_plugin_version : String by project

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.0")
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath("org.jetbrains.dokka:dokka-android-gradle-plugin:$android_gradle_plugin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("de.dynamicfiles.projects.gradle.plugins:javafx-gradle-plugin:8.8.2")

        //classpath("org.bitbucket.shemnon.javafxplugin:gradle-javafx-plugin:8.1.1")
        classpath("de.dynamicfiles.projects.gradle.plugins:javafx-gradle-plugin:8.8.2")
    }
}

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
