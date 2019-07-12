import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

apply( from = "$rootDir/common.gradle.kts" )

val kotlinXSerializationRuntimeCommon : String by extra
val kotlinXCoroutinesCore             : String by extra
val kotlinXCoroutinesIosX64           : String by extra
val ktorClientIos                     : String by extra
val kotlinXSerializationRuntimeNative : String by extra
val ktorClientJsonNative              : String by extra
val ktorClientSerializationNative     : String by extra
val kotlinXSerializationRuntimeJvm    : String by extra

buildscript {

    println("*** BuildScript: shared")

    apply( from = "common.gradle.kts")

    val kotlinVersion             : String by extra
    val kotlinSerializationPlugin : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
        maven( url = "https://kotlin.bintray.com/kotlinx" )
    }

    dependencies {
        classpath(kotlinSerializationPlugin)
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

allprojects {
    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }
}

group = "org.chrishatton"
version = "0.0.1"

kotlin {
    
    jvm {}

    // This is for iPhone emulator
    // Switch here to iosArm64 (or iosArm32) to build library for iPhone device
    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>("iosX64"), "ios") {
        binaries {
            framework {
                // Framework configuration
            }
        }
    }
    
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(kotlinXSerializationRuntimeCommon)
                implementation(kotlinXCoroutinesCore)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlinXSerializationRuntimeJvm)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(kotlinXCoroutinesIosX64)
                implementation(kotlinXSerializationRuntimeNative)
            
                implementation(ktorClientIos)
                implementation(ktorClientJsonNative)
                implementation(ktorClientSerializationNative)
            }
        }
        val iosTest by getting {
        }
    }
}
