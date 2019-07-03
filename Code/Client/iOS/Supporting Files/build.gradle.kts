
buildscript {

    apply( from = "common.gradle.kts")

    val kotlin_version : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlin_version ))
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
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
