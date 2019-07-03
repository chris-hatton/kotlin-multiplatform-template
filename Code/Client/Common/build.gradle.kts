import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

val ktor_version                 : String by extra
val kotlin_serialization_version : String by extra
val kotlin_coroutines_version    : String by extra

val kotlinXCoroutinesCore   : String by extra
val kotlinXCoroutinesIosX64 : String by extra

val ktorClientCore             : String by extra
val ktorClientCio              : String by extra
val ktorClientJson             : String by extra
val ktorClientSerializationJvm : String by extra

val ktorClientIos        : String by extra
val ktorClientCodeIosX64 : String by extra

val kotlinXSerializationRuntimeNative : String by extra
val kotlinXSerializationRuntimeCommon : String by extra

val sharedProject : ()->ProjectDependency by extra

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
                implementation(kotlinXCoroutinesIosX64)
                implementation(kotlinXSerializationRuntimeNative)
                implementation(ktorClientIos)
                implementation(ktorClientCodeIosX64)
            }
        }
        val iosTest by getting {
        }

        val jvmMain by getting {
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
        val jvmTest by getting {
            dependencies {

                implementation(kotlin("test-junit"))
                //implementation(kotlin("test-annotations"))
            }
        }
    }
}


