
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

apply( from = "../../../shared.gradle.kts")

val kotlinXCoroutinesNative           : String by extra
val ktorClientIos                     : String by extra
val kotlinXSerializationRuntimeNative : String by extra
val kotlinXSerializationRuntimeCommon : String by extra
val ktorClientCore                    : String by extra
val ktorClientJson                    : String by extra
val ktorClientJsonNative              : String by extra
val ktorClientSerializationNative     : String by extra
val ktorServerSessions                : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

val isIosDevice : Boolean by extra

plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

val frameworkAtribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

configurations {
    val metadataCompileClasspath by getting {
        attributes { attribute(frameworkAtribute, "ios") }
    }
}

kotlin {
    val iosTarget = if(isIosDevice) iosArm64("ios") else iosX64("ios")
    iosTarget.apply {
        binaries {
            framework {
                if (!isIosDevice) {
                    embedBitcode("disable")
                }
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

                implementation(ktorServerSessions)
            }
        }
        
        val iosMain by getting {

            dependencies {

                implementation(kotlinXCoroutinesNative)

                implementation(kotlinXSerializationRuntimeNative)

                implementation(ktorClientIos)
                implementation(ktorClientJsonNative)
                implementation(ktorClientSerializationNative)

                implementation(ktorServerSessions)
            }
        }
    }
}
