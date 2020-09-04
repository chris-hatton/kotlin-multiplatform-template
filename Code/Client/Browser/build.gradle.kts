
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


buildscript {

    apply( from = "../../shared.gradle.kts")

    val kotlinVersion             : String by extra
    val kotlinSerializationPlugin : String by extra
    val androidGradlePlugin       : String by extra

    val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
    repositories(configureSharedRepositories)

    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
        classpath(androidGradlePlugin)
    }
}


val kotlinVersion           : String by extra
val kotlinCoroutinesVersion : String by extra
val ktorVersion             : String by extra

val coroutinesUi : String by extra
val multiMvp     : String by extra

val kotlinXCoroutinesCore   : String by extra
val ktorClient              : String by extra
val ktorClientJson          : String by extra

val jUnit : String by extra

//val multiMvpProject     : ()->ProjectDependency by extra
val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    maven( url = "https://oss.sonatype.org/content/repositories/snapshots/" )
    maven( url = "https://oss.jfrog.org/oss-snapshot-local" ) { content { includeGroup("org.chrishatton") } }
    maven( url = "https://dl.bintray.com/chris-hatton/lib"  ) { content { includeGroup("org.chrishatton") } }
}

plugins {

    //id("com.android.library") apply false

    id("org.jetbrains.kotlin.multiplatform")
    id("kotlinx-serialization")
}


kotlin {

    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }

    sourceSets {

        js().compilations["main"].defaultSourceSet {
            dependencies {

                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")

                implementation(project(path = ":client-shared")) { attributes { attribute(frameworkAtribute, "js") } }
                implementation(project(path = ":shared"))        { attributes { attribute(frameworkAtribute, "js") } }

                implementation(coroutinesUi)
                implementation(multiMvp)

                // Kotlin Core
                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXSerializationRuntimeJs)
                implementation(kotlinXCoroutinesCoreJs)

                // Ktor
                implementation(ktorClientJs)
                implementation(ktorClientJsonJs)
                implementation(ktorClientSerializationJs)
            }
        }
        js().compilations["test"].defaultSourceSet  {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

val frameworkAtribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

configurations {
    val runtimeClasspath     by getting
    val testRuntimeClasspath by getting

    listOf(runtimeClasspath,testRuntimeClasspath).forEach { configuration ->
        configuration.attributes { attribute(frameworkAtribute, "js") }
    }
}
