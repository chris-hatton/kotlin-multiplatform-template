
buildscript {

    apply( from = "../../../shared.gradle.kts")

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

val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
repositories(configureSharedRepositories)

plugins {
    id("com.android.library") apply false
}

allprojects {

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
        maven( url = "https://dl.bintray.com/chris-hatton/lib"  ) { content { includeGroup("org.chrishatton") } }
        maven( url = "https://oss.jfrog.org/oss-snapshot-local" ) { content { includeGroup("org.chrishatton") } }
    }
}
