
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
        maven {
            setUrl("http://sandec.bintray.com/repo")
        }
    }

    dependencies {
        classpath(javaFxGradlePlugin)
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

plugins {

    id("application")
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("org.beryx.jlink") version "2.9.4"

    kotlin("jvm" ) version "1.3.40"
    id("kotlinx-serialization") version "1.3.40"
    //id("no.tornado.fxlauncher") version "1.0.15"
}



//compileKotlin {
//    kotlinOptions.jvmTarget = "1.8"
//}

//fxlauncher {
//    applicationVendor = "Tornado"
//    applicationUrl = "http://tornadofx.tornado.no/kitchensink/"
//    applicationMainClass = "tornadofx.kitchensink.app.KitchenSinkApp"
//    acceptDowngrade = false
//    //deployTarget = "w144768@tornadofx.tornado.no:www/kitchensink"
//}

application {
    mainClassName = "org.chrishatton.example.ExampleApp"
}

tasks.withType<JavaCompile> {

    group   = "org.chrishatton.example"
    version = "1.0"

    //ext["moduleName"] = moduleName

    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()

    //inputs.property("moduleName", ext["moduleName"])

    doFirst {
        println()""
        options.compilerArgs = listOf(
                "--module-path", classpath.asPath,
                "--add-modules", listOf("javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.base").joinToString(",")
        )
        classpath = files()
    }
}

tasks.withType<Jar> {
//    inputs.property("moduleName", moduleName)
//    manifest {
//        attributes("Automatic-Module-Name" to moduleName)
//    }
}

/**
 * @see [OpenJFX Docs](https://openjfx.io/openjfx-docs/))
 */
javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.base")
}

jlink {
    launcher {
        name = "org.chrishatton.example"
    }
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
    implementation(kotlinXCoroutinesCore)

    // JavaFX
    val currentOS = org.gradle.internal.os.OperatingSystem.current()
    val javaFxPlatformId : String = when {
        currentOS.isWindows -> "win"
        currentOS.isLinux   -> "linux"
        currentOS.isMacOsX  -> "mac"
        else                -> throw Exception("Unsupported OS ${currentOS.name}")
    }

    api("$javaFxBase:$javaFxPlatformId")
    api("$javaFxGraphics:$javaFxPlatformId")
    api("$javaFxControls:$javaFxPlatformId")
    api("$javaFxFxml:$javaFxPlatformId")

    implementation(tornadoFx)

    // Ktor
    implementation(ktorClient)
    implementation(ktorClientCio)
    implementation(ktorClientJson)

    testImplementation(jUnit)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}


///=========



/*
task run(type: JavaExec) {
    classpath sourceSets.main.runtimeClasspath
            main = "main.Main"
}



task createJar(type: Copy) {
    dependsOn 'jar'
    into "$buildDir/libs"
    from configurations.runtime
}
*/
