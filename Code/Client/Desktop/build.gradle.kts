
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
val kotlinStandardLibrary8  : String by extra
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
    id("application") // Is also implied by 'org.openjfx.javafxplugin', but made explicit for visibility.
    id("kotlinx-serialization") version kotlinVersion
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("org.beryx.runtime") version "1.2.1"
}

val myMainClassName = "org.chrishatton.example.ExampleApp"
val myVendor = "org.chrishatton"

application {
    applicationName = "Example"
    mainClassName = myMainClassName
}

val javaFxModules = arrayOf("javafx.controls","javafx.fxml","javafx.base","javafx.graphics")
val allModules = javaFxModules + arrayOf("java.logging")

javafx {
    version = "12"
    modules(*javaFxModules)
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

    implementation(kotlinStandardLibrary8)

    // Kotlin Core
    implementation(kotlinXCoroutinesCore)
    implementation(kotlinXCoroutinesJavaFx)

    implementation(tornadoFx)

    // Ktor
    implementation(ktorClient)
    implementation(ktorClientCio)
    implementation(ktorClientJson)

    /**
     * Regarding JavaFX dependencies:
     *
     * The project expects that JavaFX 'jmods's are present in the /jmods folder if your JDK_HOME.
     * Pre-compiled jmods can be downloaded, for major platforms, from: https://openjfx.io/
     * While it is possible, for a single platform, to handle JavaFX dependencies much like
     * any other Gradle-defined dependency, the JDK /jmods path was taken for compatibility with
     * the multi-platform workflow implied by the Beryx Runtime plugin.
     *
     * For further explanation, see this blog post: TODO write blog-post
     */

    // Test
    testImplementation(jUnit)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

val jdkFxPlatformsHomeKey = "JDK_FX_PLATFORMS_HOME"
val jdkFxPlatformsBaseFolder : String? = System.getenv(jdkFxPlatformsHomeKey)
val isMultiPlatformRuntime = (jdkFxPlatformsBaseFolder != null)
val platformIdentifiers = listOf("linux","windows","osx")

runtime {
    addOptions(
        "--strip-debug",
        "--compress", "2",
        "--no-header-files",
        "--no-man-pages"
    )

    addModules(*allModules)

    if(isMultiPlatformRuntime) {
        if(!File(jdkFxPlatformsBaseFolder).exists()) {
            throw Exception("Environment variable $jdkFxPlatformsHomeKey was set, indicating that multi-platform runtime images are desired, but the nominated folder was not found at '$jdkFxPlatformsBaseFolder'.")
        }

        println("Environment variable '$jdkFxPlatformsHomeKey' is set to '$jdkFxPlatformsBaseFolder'")
        println("Will attempt to build runtime images for: ${platformIdentifiers.joinToString(", ")}")

        val jdkBaseName = "jdk-12.0.2"

        fun createPlatformPath(identifier: String)
                = jdkFxPlatformsBaseFolder + File.separator + jdkBaseName + "-" + identifier

        platformIdentifiers.forEach { platformIdentifier ->
            val platformFolder = createPlatformPath(platformIdentifier)
            val outcomeMessage = if(File(platformFolder).exists()) {
                targetPlatform(platformIdentifier, platformFolder)
                "Will create runtime image for '$platformIdentifier' using JDK+FX folder at: '$platformFolder'"
            } else {
                "Skipping creation of runtime image for '$platformIdentifier'; missing JDK+FX folder at: '$platformFolder'"
            }
            println(outcomeMessage)
        }
    } else {
        println("Environment variable $jdkFxPlatformsHomeKey not set")
        println("Building runtime image for host platform only")
    }
}
