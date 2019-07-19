
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 *
 * Build file for the 'JavaFX Desktop' module of this Kotlin Multi-platform Application.
 *
 */

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
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
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

val kotlinXCoroutinesCore   : String by extra
val kotlinXCoroutinesJavaFx : String by extra
val tornadoFx               : String by extra
val ktorClient              : String by extra
val ktorClientCio           : String by extra
val ktorClientJson          : String by extra

val jUnit : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

val iosTargetName : String by extra

plugins {
    val kotlinVersion = "1.3.40"
    kotlin("jvm" ) version kotlinVersion
    //id("application")
    id("kotlinx-serialization") version kotlinVersion
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("org.beryx.runtime") version "1.2.1"
}

val myMainClassName = "org.chrishatton.example.ExampleApp"
val myVendor = "org.chrishatton"

application {
    mainClassName = myMainClassName
}

javafx {
    version = "12"
    modules("javafx.controls","javafx.fxml")
}

tasks.withType<KotlinCompile> {
    group   = "org.chrishatton.example"
    version = "1.0"

    sourceCompatibility = JavaVersion.VERSION_12.toString()
    targetCompatibility = JavaVersion.VERSION_12.toString()
}

tasks.withType<JavaCompile> {

    group   = "org.chrishatton.example"
    version = "1.0"

    sourceCompatibility = JavaVersion.VERSION_12.toString()
    targetCompatibility = JavaVersion.VERSION_12.toString()

    doFirst {
        options.compilerArgs = options.compilerArgs + listOf(
            "--module-path",classpath.asPath,
            "--add-modules","javafx.controls,javafx.fxml"
        )
    }
}

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    maven( url = "https://oss.sonatype.org/content/repositories/snapshots/" )
}

dependencies {

    implementation(clientCommonProject())
    implementation(sharedProject())

    implementation(kotlinStandardLibrary7)

    // Kotlin Core
    implementation(kotlinXCoroutinesCore)
    implementation(kotlinXCoroutinesJavaFx)

    implementation(tornadoFx)

    // Ktor
    implementation(ktorClient)
    implementation(ktorClientCio)
    implementation(ktorClientJson)

    // JavaFX
    compileClasspath(fileTree("/Users/Chris/Programming/javafx-jmods-12.0.1/"))

    implementation(javaFxBase)
    implementation(javaFxGraphics)
    implementation(javaFxControls)
    implementation(javaFxFxml)

    // Test
    testImplementation(jUnit)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

runtime {
    addOptions(
        "--strip-debug",
        "--compress", "2",
        "--no-header-files",
        "--no-man-pages",
        "--module-path", sourceSets["main"].compileClasspath.asPath,
        "--add-modules", "javafx.controls,javafx.fxml"
    )
}