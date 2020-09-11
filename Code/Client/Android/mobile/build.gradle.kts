
/**
 *
 * Build file for the 'Android Mobile/Tablet' module of this Kotlin Multi-platform Application.
 *
 */

apply( from = "../../../shared.gradle.kts")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.multiplatform") // We should be able to use `kotlin("android")` but Android Studio fails to resolve symbols from composite KMP libraries.
    id("kotlin-android-extensions")
    id("kotlinx-serialization")
}

val ktorClientAndroid        : String by extra
val ktorClientJson           : String by extra
val androidXAppCompat        : String by extra
val androidXCoreKtx          : String by extra
val androidXConstraintLayout : String by extra
val kotlinXCoroutinesAndroid : String by extra
val jUnit                    : String by extra
val androidXTestRunner       : String by extra
val androidXTestEspressoCore : String by extra

val androidBuildToolsVersion : String by extra
val androidCompileSdkVersion : String by extra
val androidTargetSdkVersion  : String by extra
val androidMinSdkVersion     : String by extra

val androidclientSharedProject : ()->ProjectDependency by extra
val clientSharedProject        : ()->ProjectDependency by extra
val sharedProject              : ()->ProjectDependency by extra

val multiMvp : String by extra

android {
    buildToolsVersion = androidBuildToolsVersion

    compileSdkVersion(androidCompileSdkVersion.toInt())
    defaultConfig {
        multiDexEnabled = true
        applicationId = "org.chrishatton.example"
        minSdkVersion(androidMinSdkVersion.toInt())
        targetSdkVersion(androidTargetSdkVersion.toInt())
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug"){}
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        exclude("META-INF/ktor-http.kotlin_module")
        exclude("META-INF/kotlinx-io.kotlin_module")
        exclude("META-INF/atomicfu.kotlin_module")
        exclude("META-INF/ktor-utils.kotlin_module")
        exclude("META-INF/kotlinx-coroutines-io.kotlin_module")
        exclude("META-INF/kotlinx-coroutines-core.kotlin_module")
        exclude("META-INF/ktor-client-core.kotlin_module")
        exclude("META-INF/ktor-http-cio.kotlin_module")
        exclude("META-INF/ktor-client-json.kotlin_module")
        exclude("META-INF/kotlinx-serialization-runtime.kotlin_module")
        exclude("META-INF/multi-mvp_release.kotlin_module")
        exclude("META-INF/ktor-io.kotlin_module")
    }
    sourceSets {
        this["main"].java.srcDir("src/androidMain/kotlin")
        this["test"].java.srcDir("src/androidTest/kotlin")
        this["androidTest"].java.srcDir("src/androidAndroidTest/kotlin")
    }
}

@Suppress("UNCHECKED_CAST")

val frameworkAttribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

kotlin {
    android("android") {
        attributes.attribute(frameworkAttribute, "android")
        //publishLibraryVariants("release", "debug") // Required for Android to publish
        publishLibraryVariantsGroupedByFlavor = true
    }

    sourceSets {
        val androidMain by getting {
            dependencies {

                // Projects
                implementation(androidclientSharedProject())
                implementation(clientSharedProject())
                implementation(sharedProject())

                implementation(multiMvp)

                // Ktor
                implementation(ktorClientAndroid)
                implementation(ktorClientJson)

                // Android
                implementation(androidXAppCompat)
                implementation(androidXCoreKtx)
                implementation(androidXConstraintLayout)
                implementation(kotlinXCoroutinesAndroid)
            }
        }

        val androidTest by getting {
            dependencies {
                // Test
                implementation(jUnit)

                // Android Test
                implementation(androidXTestRunner)
                implementation(androidXTestEspressoCore)
            }
        }
    }
}
