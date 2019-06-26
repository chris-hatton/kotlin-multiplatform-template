import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

apply {
    from("$rootDir/common.gradle.kts")
}

val kotlin_version               : String by project
val ktor_version                 : String by project
val kotlin_serialization_version : String by project
val kotlin_coroutines_version    : String by project

buildscript {

    // Hack to get common properties read at this point,
    // since Gradle (Kotlin DSL) has no way to include a function.
    run {
        java.util.Properties().apply {
            File("$rootDir/common.properties").inputStream().use { fis ->
                load(fis)
            }
        }.forEach {
            extra[it.key.toString()] = it.value
        }
    }

    val kotlin_version : String by project

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
        maven( url = "https://kotlin.bintray.com/kotlinx" )
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath(kotlin("gradle-plugin", version = kotlin_version))
    }
}

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

allprojects {
    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }
}

group = "org.chrishatton"
version = "0.0.1"

kotlin {
    
//    targets {
//        jvm()
//    }
    
    jvm {}

    // This is for iPhone emulator
    // Switch here to iosArm64 (or iosArm32) to build library for iPhone device
    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>("iosX64"), "ios") {
        binaries {
            framework {
                // Framework configuration
            }
        }
    }
    
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$kotlin_serialization_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                //implementation("io.ktor:ktor-client:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlin_serialization_version")
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization:$kotlin_serialization_version")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
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
        val iosTest by getting {
        }
    }
}
