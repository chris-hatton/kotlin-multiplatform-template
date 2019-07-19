/**
 *
 * Build file for the 'Client-Common' module of this Kotlin Multi-platform Application.
 *
 * Source files implemented in this module are accessible to all Client projects.
 * UI Presentation logic and other Client-side business logic should reside in this module.
 * The Presenters of the MVP architecture should be implemented in this module.
 *
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

buildscript {

    apply( from = "common.gradle.kts")

    val kotlinVersion             : String by extra
    val kotlinSerializationPlugin : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
    }
}

val kotlinXCoroutinesCore : String by extra
val kotlinXCoroutinesIos  : String by extra

val ktorClientCore             : String by extra
val ktorClientCio              : String by extra
val ktorClientJson             : String by extra
val ktorClientSerializationJvm : String by extra

val ktorClientIos     : String by extra
val ktorClientCodeIos : String by extra

val kotlinXSerializationRuntimeNative : String by extra
val kotlinXSerializationRuntimeCommon : String by extra

val sharedProject : ()->ProjectDependency by extra

val iosTargetName : String by extra

plugins {
    kotlin("multiplatform")
}

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

kotlin {

    jvm {}

    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>(iosTargetName), "ios") {
        binaries {
            framework {
                // Framework configuration
                //embedBitcode(Framework.BitcodeEmbeddingMode.BITCODE)
            }
        }
    }

    sourceSets {

        commonMain {
            dependencies {
                implementation(sharedProject())
                implementation(kotlin("stdlib-common"))
                implementation(ktorClientCore)
                implementation(kotlinXCoroutinesCore)
                implementation(ktorClientCio)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(sharedProject())

                implementation(kotlinXCoroutinesIos)

                implementation(kotlinXSerializationRuntimeNative)
                implementation(ktorClientIos)
            }
        }
        val iosTest by getting {
        }

        val jvmMain by getting {
            dependencies {
                implementation(sharedProject())
                implementation(kotlin("stdlib-common"))
                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXSerializationRuntimeCommon)

                implementation(ktorClientCore)
                implementation(ktorClientCio)
                implementation(ktorClientJson)
                implementation(ktorClientSerializationJvm)

            }
        }
        val jvmTest by getting {
            dependencies {

                implementation(kotlin("test-junit"))
                //implementation(kotlin("test-annotations"))
            }
        }
    }
}


