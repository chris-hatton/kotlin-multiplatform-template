
val kotlin_version            : String by extra
val kotlin_coroutines_version : String by extra
val ktor_version              : String by extra
val tornadofx_version         : String by extra

plugins {
    kotlin("jvm" ) version "1.3.40"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("kotlinx-serialization") version "1.3.40"
}

buildscript {

    apply( from = "common.gradle.kts")

    val kotlin_version               : String by extra
    val javafx_gradle_plugin_version : String by extra
    val javafx_plugin_version        : String by extra

    println("-$kotlin_version-")

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.openjfx:javafx-plugin:$javafx_plugin_version")
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("de.dynamicfiles.projects.gradle.plugins:javafx-gradle-plugin:$javafx_gradle_plugin_version")
    }
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml")
}

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}

dependencies {

    implementation(project(":client-common"))
    implementation(project(":shared"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")

    // Kotlin Core
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")

    // JavaFX
    val currentOS = org.gradle.internal.os.OperatingSystem.current()
    val javaFxPlatformId : String = when {
        currentOS.isWindows -> "win"
        currentOS.isLinux   -> "linux"
        currentOS.isMacOsX  -> "mac"
        else                -> throw Exception("Unsupported OS ${currentOS.name}")
    }

    implementation("org.openjfx:javafx-base:11:${javaFxPlatformId}")
    implementation("org.openjfx:javafx-graphics:11:${javaFxPlatformId}")
    implementation("org.openjfx:javafx-controls:11:${javaFxPlatformId}")

    implementation("no.tornado:tornadofx:$tornadofx_version")

    // Ktor
    implementation("io.ktor:ktor-client:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-json:$ktor_version")

//    testImplementation(kotlin("test"))
//    testImplementation(kotlin("test-junit"))
}

///=========

//group 'Project'
//version '1.0'
//ext.moduleName = 'Project.main'
//sourceCompatibility = 1.11


/*
task run(type: JavaExec) {
    classpath sourceSets.main.runtimeClasspath
            main = "main.Main"
}

jar {
    inputs.property("moduleName", moduleName)
    manifest {
        attributes('Automatic-Module-Name': moduleName)
    }
}

compileJava {
    inputs.property("moduleName", moduleName)
    doFirst {
        options.compilerArgs = [
            '--module-path', classpath.asPath,
            '--add-modules', 'javafx.controls'
        ]
        classpath = files()
    }
}

task createJar(type: Copy) {
    dependsOn 'jar'
    into "$buildDir/libs"
    from configurations.runtime
}
*/
