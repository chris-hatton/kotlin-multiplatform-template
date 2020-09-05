/**
 *1.
 * Build file for the 'Client-shared' module of this Kotlin Multi-platform Application.
 *
 * Source files implemented in this module are accessible to all Client projects.
 * UI Presentation logic and other Client-side business logic should reside in this module.
 * The Presenters of the MVP architecture should be implemented in this module.
 *
 */

buildscript {

    apply( from = "../../shared.gradle.kts")

    val kotlinVersion             : String by extra
    val kotlinSerializationPlugin : String by extra
    val androidGradlePlugin       : String by extra

    val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
    repositories(configureSharedRepositories)

    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
        classpath(androidGradlePlugin)
    }
}

val isMinJava12 : Boolean = JavaVersion.current() >= JavaVersion.VERSION_12

val kotlinXCoroutinesCore   : String by extra

val multiMvp : String by extra

val kotlinXSerializationRuntime : String by extra

val ktorClient                    : String by extra
val ktorClientAndroid             : String by extra
val ktorClientIos                 : String by extra
val ktorClientCore                : String by extra
val ktorClientCoreJvm             : String by extra
val ktorClientCoreNative          : String by extra
val ktorClientJson                : String by extra
val ktorClientSerialization       : String by extra
val ktorClientAuth                : String by extra

//val multiMvpProject : ()->ProjectDependency by extra
val sharedProject   : ()->ProjectDependency by extra

val isIosDevice : Boolean by extra

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
}

val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
repositories(configureSharedRepositories)

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

    android("android") {
        attributes.attribute(frameworkAtribute, "android")
    }

    if(isMinJava12) {
        jvm("javafx") { attributes.attribute(frameworkAtribute, "javafx") }
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
        attributes.attribute(frameworkAtribute, "ios")
    }

    js("browser",IR) {
        attributes.attribute(frameworkAtribute, "js")
        browser {
            attributes.attribute(frameworkAtribute, "js")
        }
        attributes.attribute(frameworkAtribute, "js")
    }

    sourceSets {

        commonMain {
            dependencies {

                println("Common depending on $multiMvp")
                implementation(multiMvp)

                implementation(project(path = ":shared"))

                implementation(kotlin("stdlib-common"))

                implementation(ktorClientCore)
                implementation(kotlinXCoroutinesCore)
                implementation(ktorClientJson)
                implementation(ktorClientSerialization)
                implementation(ktorClientAuth)
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
                implementation(kotlin("stdlib"))

                println("iOS depending on $multiMvp")
                implementation(multiMvp)
                //implementation(coroutinesUi)

                implementation(kotlinXCoroutinesCore)

                implementation(kotlinXSerializationRuntime)
                implementation(ktorClientIos)
                implementation(ktorClientJson)
                implementation(ktorClientSerialization)
            }
        }
        val iosTest by getting {
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))

                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXSerializationRuntime)

                println("Android depending on $multiMvp")
                implementation(multiMvp)

                implementation(ktorClientCore)
                implementation(ktorClientJson)
                implementation(ktorClientSerialization)
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
                    implementation(kotlin("stdlib"))

                    implementation(multiMvp)

                    implementation(kotlinXCoroutinesCore)
                    implementation(kotlinXSerializationRuntime)

                    implementation(ktorClientCore)
                    implementation(ktorClientJson)
                    implementation(ktorClientSerialization)
                }
            }
            val javafxTest by getting {
                dependencies {

                    implementation(kotlin("test-junit"))
                    //implementation(kotlin("test-annotations"))
                }
            }
        }

        val browserMain by getting {
            dependencies {

            }
        }

        val browserTest by getting {
            dependencies {

            }
        }
    }
}

