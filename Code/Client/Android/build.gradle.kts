
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    apply( from = "../../shared.gradle.kts")

    val kotlinVersion             : String by extra
    val androidGradlePlugin       : String by extra
    val androidGradleDokkaPlugin  : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }

    dependencies {
        classpath(androidGradlePlugin)
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(androidGradleDokkaPlugin)
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

allprojects {

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
