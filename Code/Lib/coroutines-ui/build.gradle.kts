
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply( from = "../../shared.gradle.kts")
apply( from = "../javafx.build.gradle.kts" )

val isMinJava12 : Boolean by extra
val javaFxSdkHome : String by extra

val kotlinXCoroutinesCoreCommon : String by extra
val kotlinXCoroutinesCore       : String by extra
val kotlinXCoroutinesNative     : String by extra
val kotlinXCoroutinesAndroid    : String by extra
val kotlinXCoroutinesJavaFx     : String by extra

val tornadoFx : String by extra

val iosTargetName : String by extra

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://oss.sonatype.org/content/repositories/snapshots/" )
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
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

val frameworkAtribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

val javaFxModules = arrayOf("javafx.controls","javafx.fxml","javafx.base","javafx.graphics")
val allModules = javaFxModules + arrayOf("java.logging")

val versionName = "0.0.1"

group   = "org.chrishatton"
version = versionName

tasks.withType<KotlinCompile> {
    sourceCompatibility = JavaVersion.VERSION_12.toString()
    targetCompatibility = JavaVersion.VERSION_12.toString()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_12.toString()
    targetCompatibility = JavaVersion.VERSION_12.toString()
}

kotlin {

    android("android") {
        attributes.attribute(frameworkAtribute, "android")
        publishLibraryVariants("release", "debug") // Required for Android to publish
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
                    implementation(tornadoFx)

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

val bintrayPublishUrl = "https://api.bintray.com/maven/$bintrayUser/$bintrayRepo/coroutines-ui/;publish=0;override=1"
logger.info("Publish URL: $bintrayPublishUrl")

publishing {
    repositories {
        maven(bintrayPublishUrl) {
            name = "bintray"
            credentials {
                username = bintrayUser
                password = bintrayKey
            }
        }
    }
}
