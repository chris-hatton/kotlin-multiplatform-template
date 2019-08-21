import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

/**
 *
 * Build file for the 'Shared' module of this Kotlin Multi-platform Application.
 *
 * Source files implemented in this module are accessible in the Server and Client projects.
 * It is particularly useful to implement Model files in Shared.
 *
 */

buildscript {

    // Reads 'common.properties' into Gradle extra
    apply( from = "../shared.gradle.kts")

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

val kotlinXSerializationRuntimeCommon : String by extra
val kotlinXCoroutinesCore             : String by extra
val kotlinXCoroutinesNative           : String by extra
val ktorClientIos                     : String by extra
val kotlinXSerializationRuntimeNative : String by extra
val ktorClientJsonNative              : String by extra
val ktorClientSerializationNative     : String by extra
val kotlinXSerializationRuntimeJvm    : String by extra

val iosTargetName : String by extra

val isMinJava12 : Boolean = JavaVersion.current() >= JavaVersion.VERSION_12

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
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

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    sourceSets {
        get("main").apply {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
        get("test").apply {
            java.srcDirs("src/androidTest/kotlin")
            res.srcDirs("src/androidTest/res")
        }
    }
}

val frameworkAtribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

kotlin {
    
    android { attributes.attribute(frameworkAtribute, "android") }

    if(isMinJava12) {
        jvm("javafx") {attributes.attribute(frameworkAtribute, "javafx") }
    }

    jvm("server") {
        attributes.attribute(frameworkAtribute, "server")
    }

    // This is for iPhone emulator
    // Switch here to iosArm64 (or iosArm32) to build library for iPhone device
    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>(iosTargetName), "ios") {
        binaries {
            framework {
                // Framework configuration
            }
        }
        attributes.attribute(frameworkAtribute, "ios")
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

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlinXSerializationRuntimeJvm)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        if(isMinJava12) {
            val javafxMain by getting {
                dependencies {
                    implementation(kotlin("stdlib"))
                    implementation(kotlinXSerializationRuntimeJvm)
                }
            }
            val javafxTest by getting {
                dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-junit"))
                }
            }
        }

        val serverMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlinXSerializationRuntimeJvm)
            }
        }
        val serverTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        val iosMain by getting {
            dependencies {

                implementation(kotlinXCoroutinesNative)

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
