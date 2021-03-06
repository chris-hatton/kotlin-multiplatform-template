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

    val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
    repositories(configureSharedRepositories)

    dependencies {
        classpath(kotlinSerializationPlugin)
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}

val kotlinXSerializationRuntime : String by extra
val kotlinXCoroutinesCore       : String by extra
val ktorServerSessions          : String by extra
val ktorAuth                    : String by extra

val isIosDevice : Boolean by extra

val isMinJava12 : Boolean = JavaVersion.current() >= JavaVersion.VERSION_12

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("kotlinx-serialization")
}

val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
repositories(configureSharedRepositories)

allprojects {
    repositories(configureSharedRepositories)
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
        val main by getting {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
        val test by getting {
            java.srcDirs("src/androidTest/kotlin")
            res.srcDirs("src/androidTest/res")
        }
    }
}

val frameworkAttribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

kotlin {
    
    android("android") { attributes.attribute(frameworkAttribute, "android") }

    if(isMinJava12) {
        jvm("javafx") {attributes.attribute(frameworkAttribute, "javafx") }
    }

    jvm("server") {
        attributes.attribute(frameworkAttribute, "server")
    }

    val iosTarget = if(isIosDevice) iosArm64("ios") else iosX64("ios")
    iosTarget.apply {
        binaries {
            framework {
                if (!isIosDevice) {
                    embedBitcode("disable")
                }
            }
        }
        attributes.attribute(frameworkAttribute, "ios")
    }

    js("browser",IR) {
        browser {
        }
        binaries.executable()
        attributes.attribute(frameworkAttribute, "js")
    }
    
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(kotlinXSerializationRuntime)
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
                implementation(kotlinXSerializationRuntime)
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
                    implementation(kotlinXSerializationRuntime)
                }
            }
            val javafxTest by getting {
                dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-junit"))
                }
            }
        }

        val browserMain by getting {
            dependencies {
                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXSerializationRuntime)
            }
        }

        val browserTest by getting {
            dependencies {

            }
        }

        val serverMain by getting {
            dependencies {
                implementation(kotlinXSerializationRuntime)
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
                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXSerializationRuntime)
            }
        }
        val iosTest by getting {
        }
    }
}
