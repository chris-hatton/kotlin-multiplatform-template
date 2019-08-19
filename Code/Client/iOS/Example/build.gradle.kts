
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

apply( from = "../../../common.gradle.kts")

val kotlinXCoroutinesNative           : String by extra
val ktorClientIos                     : String by extra
val kotlinXSerializationRuntimeNative : String by extra
val kotlinXSerializationRuntimeCommon : String by extra
val ktorClientCore                    : String by extra
val ktorClientJson                    : String by extra
val ktorClientJsonNative              : String by extra
val ktorClientSerializationNative     : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

val iosTargetName : String by extra

plugins {
    kotlin("multiplatform")
    kotlin("xcode-compat") version "0.1"
}

val frameworkAtribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

configurations {
    val metadataCompileClasspath by getting {
        attributes { attribute(frameworkAtribute, "ios") }
    }
//    val iosCompileKlibraries by getting {
//        attributes { attribute(frameworkAtribute, "ios") }
//    }
}

kotlin {
//    xcode {
//        setupApplication("ios")
//    }

    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>(iosTargetName), "ios") {
        binaries {
            framework {
                // Framework configuration
                //embedBitcode(Framework.BitcodeEmbeddingMode.BITCODE)
            }
        }
        attributes { attribute(frameworkAtribute, "ios") }
    }

    targets {
        // Target setup
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(project(":client-shared"))
                implementation(project(":shared"))

                implementation(ktorClientCore)
                implementation(ktorClientJson)
                implementation(kotlinXSerializationRuntimeCommon)
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
    }
}
