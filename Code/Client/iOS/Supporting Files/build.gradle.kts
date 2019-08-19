
buildscript {

    apply( from = "common.gradle.kts")

    val kotlinSerializationPlugin : String by extra
    val kotlinGradlePlugin        : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }

    dependencies {
        classpath(kotlinGradlePlugin)
        classpath(kotlinSerializationPlugin)
    }
}

plugins {
    id("com.android.library") apply false
}

allprojects {

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }
}
