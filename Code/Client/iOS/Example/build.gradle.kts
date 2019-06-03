
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

val kotlin_version               : String = extra["kotlin_version"].toString()
val kotlin_coroutines_version    : String = extra["kotlin_coroutines_version"].toString()
val ktor_version                 : String = extra["ktor_version"].toString()
val kotlin_serialization_version : String = extra["kotlin_serialization_version"].toString()

plugins {
    kotlin("multiplatform") version "1.3.31"
    kotlin("xcode-compat") version "0.1"
}

kotlin {
    xcode {
        setupApplication("Example")
    }
    
    targets {
        logger.lifecycle("Targets: ${this.toString()}")
    }

    // This is for iPhone emulator
    // Switch here to iosArm64 (or iosArm32) to build library for iPhone device
    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>("iosX64"), "ios") {
        binaries {
            framework {
                // Framework configuration
                embedBitcode(Framework.BitcodeEmbeddingMode.BITCODE)
            }
        }
    }

    sourceSets {

        logger.lifecycle("SourceSets: ${this.toString()}")
        
        val iosMain by getting {
            dependencies {
                
                implementation(project(":client-common"))
                implementation(project(":shared"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$kotlin_coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$kotlin_serialization_version")
                implementation("io.ktor:ktor-client:$ktor_version")
                implementation("io.ktor:ktor-client-core:$ktor_version")
            }
        }
    }
}
