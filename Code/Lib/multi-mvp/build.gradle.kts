
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

apply( from = "../../shared.gradle.kts")
apply( from = "../javafx.build.gradle.kts" )

val isMinJava12   : Boolean by extra
val javaFxSdkHome : String  by extra

val kotlinXCoroutinesCoreCommon : String by extra
val kotlinXCoroutinesCore       : String by extra
val kotlinXCoroutinesNative     : String by extra
val kotlinXCoroutinesAndroid    : String by extra
val kotlinXCoroutinesJavaFx     : String by extra

val iosTargetName : String by extra

val publishVersion = "0.0.1-SNAPSHOT"

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

group   = "org.chrishatton"
version = publishVersion

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = publishVersion
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    // The Android Plugin is not aware of the Kotlin Multi-platform folder structure, so needs direction to these files.
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

// Used to disambiguate same-platform targets i.e. Both JavaFx and Server are JVM
val frameworkAtribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

kotlin {

    android("android") {
        attributes.attribute(frameworkAtribute, "android")
        publishLibraryVariants("release", "debug") // Required for Android to publish
    }

    // We are using JavaFx 12, so if the host machine isn't running JRE 12+ compilation will fail.
    if(isMinJava12) {
        jvm("javafx") {
            attributes.attribute(frameworkAtribute, "javafx")
        }
    } else {
        logger.warn("JavaFx target will not be built because the host machine isn't running JDK >= 12.")
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

                    compileOnly(fileTree("$javaFxSdkHome/lib"))
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

val bintrayRepo      = findProperty("bintray.repo") as? String
val bintrayUser      = findProperty("bintray.user") as? String
val bintrayKey       = findProperty("bintray.key" ) as? String

val bintrayPublishUrl       = "https://api.bintray.com/maven/$bintrayUser/$bintrayRepo/multi-mvp"
val jfrogSnapshotPublishUrl = "http://oss.jfrog.org/artifactory/oss-snapshot-local"

publishing {
    repositories {
        if(publishVersion.endsWith("-SNAPSHOT")) {
            // Publish snapshot to JFrog
            maven(jfrogSnapshotPublishUrl) {
                name = "jfrog-snapshots"
                credentials {
                    username = bintrayUser
                    password = bintrayKey
                }
            }
        } else {
            // Publish release to Bintray
            maven(bintrayPublishUrl) {
                name = "bintray"
                credentials {
                    username = bintrayUser
                    password = bintrayKey
                }
            }
        }
    }
}
