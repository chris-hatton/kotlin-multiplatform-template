
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

buildscript {

    apply( from = "common.gradle.kts")

    val kotlinVersion             : String by extra
    val kotlinSerializationPlugin : String by extra
    val androidGradlePlugin       : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
        classpath(androidGradlePlugin)
    }
}

val isMinJava12 : Boolean = JavaVersion.current() >= JavaVersion.VERSION_12

val kotlinXCoroutinesCoreCommon : String by extra
val kotlinXCoroutinesCore       : String by extra
val kotlinXCoroutinesNative     : String by extra
val kotlinXCoroutinesAndroid    : String by extra
val kotlinXCoroutinesJavaFx     : String by extra

val iosTargetName : String by extra

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

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

    android() {
        attributes.attribute(frameworkAtribute, "android")
    }

    if(isMinJava12) {
        jvm("javafx") {
            attributes.attribute(frameworkAtribute, "javafx")
        }
    }

    targetFromPreset(presets.getByName<KotlinNativeTargetPreset>(iosTargetName), "ios") {
        binaries {
            framework {
                // Framework configuration
                //embedBitcode(Framework.BitcodeEmbeddingMode.BITCODE)
            }
        }
        attributes.attribute(frameworkAtribute, "ios")
    }

    sourceSets {

        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXCoroutinesCoreCommon)
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
                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXCoroutinesNative)
            }
        }
        val iosTest by getting {
        }

        if(isMinJava12) {
            val javafxMain by getting {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                    implementation(kotlinXCoroutinesCore)
                    implementation(kotlinXCoroutinesJavaFx)
                }
            }

            val javafxTest by getting {
                dependencies {
                    implementation(kotlin("test-junit"))
                }
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(kotlinXCoroutinesCore)
                implementation(kotlinXCoroutinesAndroid)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
