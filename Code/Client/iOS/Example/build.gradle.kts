
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

val kotlin_version               : String = extra["kotlin_version"].toString()
val kotlin_coroutines_version    : String = extra["kotlin_coroutines_version"].toString()
val ktor_version                 : String = extra["ktor_version"].toString()
val kotlin_serialization_version : String = extra["kotlin_serialization_version"].toString()

plugins {
    kotlin("multiplatform")
    kotlin("xcode-compat") version "0.1"
}

kotlin {
    xcode {
        setupApplication("ios")
    }

    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>("iosX64"), "ios") {
        binaries {
            framework {
                // Framework configuration
                //embedBitcode(Framework.BitcodeEmbeddingMode.BITCODE)
            }
        }
    }

    targets {
        // Target setup
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(project(":client-common"))
                implementation(project(":shared"))

                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-json:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlin_serialization_version")
            }
        }
        
        val iosMain by getting {

            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:$kotlin_coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$kotlin_serialization_version")

                implementation("io.ktor:ktor-client-ios:${ktor_version}")
                implementation("io.ktor:ktor-client-json-native:${ktor_version}")
                implementation("io.ktor:ktor-client-serialization-native:${ktor_version}")
            }
        }
    }
}
