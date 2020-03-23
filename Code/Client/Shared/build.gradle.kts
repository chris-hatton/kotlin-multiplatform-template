/**
 *1.
 * Build file for the 'Client-shared' module of this Kotlin Multi-platform Application.
 *
 * Source files implemented in this module are accessible to all Client projects.
 * UI Presentation logic and other Client-side business logic should reside in this module.
 * The Presenters of the MVP architecture should be implemented in this module.
 *
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

buildscript {

    apply( from = "../../shared.gradle.kts")

    val kotlinVersion             : String by extra
    val kotlinSerializationPlugin : String by extra
    val androidGradlePlugin       : String by extra

    repositories {
        mavenLocal()
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://oss.jfrog.org/oss-snapshot-local" ) { content { includeGroup("org.chrishatton") } }
        maven( url = "https://dl.bintray.com/chris-hatton/lib"  ) { content { includeGroup("org.chrishatton") } }
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
        classpath(androidGradlePlugin)
    }
}

val isMinJava12 : Boolean = JavaVersion.current() >= JavaVersion.VERSION_12

val kotlinXCoroutinesCore   : String by extra
val kotlinXCoroutinesNative : String by extra

val multiMvp : String by extra

val ktorClientCore             : String by extra
val ktorClientCio              : String by extra
val ktorClientJson             : String by extra
val ktorClientSerializationJvm : String by extra

val ktorClientIos     : String by extra
val ktorClientCodeIos : String by extra

val kotlinXSerializationRuntimeNative : String by extra
val kotlinXSerializationRuntimeCommon : String by extra

val multiMvpProject : ()->ProjectDependency by extra
val sharedProject   : ()->ProjectDependency by extra

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    maven( url = "https://oss.jfrog.org/oss-snapshot-local" ) { content { includeGroup("org.chrishatton") } }
    maven( url = "https://dl.bintray.com/chris-hatton/lib"  ) { content { includeGroup("org.chrishatton") } }
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

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

//configurations {
//	val iosCompileKlibraries by getting {
//
//    }
//}

val frameworkAtribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

kotlin {

    android { attributes.attribute(frameworkAtribute, "android") }

    if(isMinJava12) {
        jvm("javafx") { attributes.attribute(frameworkAtribute, "javafx") }
    }

    ios {
        binaries {
            framework {
                // Framework configuration
                //embedBitcode(Framework.BitcodeEmbeddingMode.BITCODE)
            }
        }
        attributes.attribute(frameworkAtribute, "ios")
    }

    sourceSets {

        commonMain {
            dependencies {

                api(multiMvp)

                implementation(project(path = ":shared"))

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

                implementation(kotlinXCoroutinesNative)

                implementation(kotlinXSerializationRuntimeNative)
                implementation(ktorClientIos)
            }
        }
        val iosTest by getting {
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXSerializationRuntimeCommon)

                implementation(ktorClientCore)
                implementation(ktorClientCio)
                implementation(ktorClientJson)
                implementation(ktorClientSerializationJvm)

            }
        }
        val androidTest by getting {
            dependencies {

                implementation(kotlin("test-junit"))
                //implementation(kotlin("test-annotations"))
            }
        }

        if(isMinJava12) {
            val javafxMain by getting {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                    implementation(kotlinXCoroutinesCore)
                    implementation(kotlinXSerializationRuntimeCommon)

                    implementation(ktorClientCore)
                    implementation(ktorClientCio)
                    implementation(ktorClientJson)
                    implementation(ktorClientSerializationJvm)

                }
            }
            val javafxTest by getting {
                dependencies {

                    implementation(kotlin("test-junit"))
                    //implementation(kotlin("test-annotations"))
                }
            }
        }
    }
}


