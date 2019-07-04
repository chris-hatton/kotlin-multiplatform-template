
val kotlinVersion           : String by extra
val kotlinCoroutinesVersion : String by extra
val ktorVersion             : String by extra
val tornadoFxVersion        : String by extra
val kotlinStandardLibrary7  : String by extra
val javaFxBase              : String by extra
val javaFxGraphics          : String by extra
val javaFxControls          : String by extra

val kotlinXCoroutinesCore : String by extra
val tornadoFx             : String by extra
val ktorClient            : String by extra
val ktorClientCio         : String by extra
val ktorClientJson        : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

plugins {
    kotlin("jvm" ) version "1.3.40"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("kotlinx-serialization") version "1.3.40"
}

buildscript {

    apply( from = "common.gradle.kts")

    val kotlinVersion             : String by extra
    val javaFxGradlePlugin        : String by extra
    val kotlinSerializationPlugin : String by extra

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
        classpath(javaFxGradlePlugin)
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
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

    implementation(clientCommonProject())
    implementation(sharedProject())

    implementation(kotlinStandardLibrary7)

    // Kotlin Core
    implementation(kotlin("stdlib"))
    implementation(kotlinXCoroutinesCore)

    // JavaFX
    val currentOS = org.gradle.internal.os.OperatingSystem.current()
    val javaFxPlatformId : String = when {
        currentOS.isWindows -> "win"
        currentOS.isLinux   -> "linux"
        currentOS.isMacOsX  -> "mac"
        else                -> throw Exception("Unsupported OS ${currentOS.name}")
    }

    implementation("$javaFxBase:$javaFxPlatformId")
    implementation("$javaFxGraphics:$javaFxPlatformId")
    implementation("$javaFxControls:$javaFxPlatformId")

    implementation(tornadoFx)

    // Ktor
    implementation(ktorClient)
    implementation(ktorClientCio)
    implementation(ktorClientJson)
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
