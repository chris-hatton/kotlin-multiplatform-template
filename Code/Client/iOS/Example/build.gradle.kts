
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

apply( from = "../../../common.gradle.kts")

val kotlinXCoroutinesIosArm64         : String by extra
val kotlinXCoroutinesIosX64           : String by extra
val ktorClientIos                     : String by extra
val kotlinXSerializationRuntimeNative : String by extra
val kotlinXSerializationRuntimeCommon : String by extra
val ktorClientCore                    : String by extra
val ktorClientJson                    : String by extra
val ktorClientJsonNative              : String by extra
val ktorClientSerializationNative     : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

val isIosArm64 : Boolean by extra

val iosTargetPresetName = if(isIosArm64) "iosArm64" else "iosX64"

plugins {
    kotlin("multiplatform")
    kotlin("xcode-compat") version "0.1"
}

kotlin {
    xcode {
        setupApplication("ios")
    }

    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>(iosTargetPresetName), "ios") {
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
                implementation(clientCommonProject())
                implementation(sharedProject())

                implementation(ktorClientCore)
                implementation(ktorClientJson)
                implementation(kotlinXSerializationRuntimeCommon)
            }
        }
        
        val iosMain by getting {

            dependencies {

                if(isIosArm64) {
                    implementation(kotlinXCoroutinesIosArm64)
                } else {
                    implementation(kotlinXCoroutinesIosX64)
                }

                implementation(kotlinXSerializationRuntimeNative)

                implementation(ktorClientIos)
                implementation(ktorClientJsonNative)
                implementation(ktorClientSerializationNative)
            }
        }
    }
}
