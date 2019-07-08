import de.dynamicfiles.projects.gradle.plugins.javafx.tasks.JfxJarTask

buildscript {

    apply( from = "common.gradle.kts")

    val kotlinVersion             : String by extra
    val kotlinSerializationPlugin : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
        maven {
            setUrl("http://sandec.bintray.com/repo")
        }
    }

    dependencies {
        classpath("de.dynamicfiles.projects.gradle.plugins:javafx-gradle-plugin:8.8.2")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
        classpath("no.tornado:fxlauncher-gradle-plugin:1.0.15")
    }
}

val kotlinVersion           : String by extra
val kotlinCoroutinesVersion : String by extra
val ktorVersion             : String by extra
val tornadoFxVersion        : String by extra
val kotlinStandardLibrary7  : String by extra
val javaFxBase              : String by extra
val javaFxGraphics          : String by extra
val javaFxControls          : String by extra
val javaFxFxml              : String by extra

val kotlinXCoroutinesCore : String by extra
val tornadoFx             : String by extra
val ktorClient            : String by extra
val ktorClientCio         : String by extra
val ktorClientJson        : String by extra

val jUnit : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

//val moduleName = "exampleApp"

apply( plugin = "javafx-gradle-plugin" )

plugins {

    id("application")
    //id("javafx-gradle-plugin")

    kotlin("jvm" ) version "1.3.40"
    id("kotlinx-serialization") version "1.3.40"
    //id("no.tornado.fxlauncher") version "1.0.15"
}

tasks.withType<JfxJarTask> {
//    // minimal requirement for jfxJar-task
//    mainClass("full.qualified.nameOf.TheMainClass")
//
//    // minimal requirement for jfxNative-task
//    vendor = "org.chrishatton"
}

application {
    mainClassName = "org.chrishatton.example.ExampleApp"
}

tasks.withType<JavaCompile> {

    group   = "org.chrishatton.example"
    version = "1.0"

    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()

}

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

dependencies {

    implementation(clientCommonProject())
    implementation(sharedProject())

    implementation(kotlinStandardLibrary7)

    // Kotlin Core
    implementation(kotlinXCoroutinesCore)

    implementation(tornadoFx)

    // Ktor
    implementation(ktorClient)
    implementation(ktorClientCio)
    implementation(ktorClientJson)

    testImplementation(jUnit)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
