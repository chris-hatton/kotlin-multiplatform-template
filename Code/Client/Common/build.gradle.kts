import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

val ktor_version                 : String by project
val kotlin_serialization_version : String by project
val kotlin_coroutines_version    : String by project

buildscript {

    val kotlin_version : String by project

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    }
}

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

    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>("iosX64"), "ios") {
        binaries {
            framework {
                // Framework configuration
                embedBitcode(Framework.BitcodeEmbeddingMode.BITCODE)
            }
        }
    }

    sourceSets {

        commonMain {
            dependencies {
                implementation(project(":shared"))
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1")
                implementation("io.ktor:ktor-client-cio:$ktor_version")
                //implementation("io.ktor:ktor-client-websocket:$ktor_version")
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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:$kotlin_coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$kotlin_serialization_version")
                implementation("io.ktor:ktor-client-ios:$ktor_version")
                implementation("io.ktor:ktor-client-core-iosx64:$ktor_version")
            }
        }
        val iosTest by getting {
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
                implementation("io.ktor:ktor-client-cio:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$kotlin_serialization_version")
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


