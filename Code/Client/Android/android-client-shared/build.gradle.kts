/**
 *
 * Build file for the 'Android-Client-shared' module of this Kotlin Multi-platform Application.
 *
 * Source files implemented in this module are accessible to all Android Client projects.
 * Currently this means to both the Android Mobile/Tablet module and the Android TV module.
 * Typically this module would be used to share common Fragments, custom Views or other parts
 * of the View layer (of the MVP architecture) which it is not desirable to re-implement
 * for both Android projects.
 *
 */

apply( from = "$rootDir/common.gradle.kts")

val androidBuildToolsVersion : String by extra
val androidCompileSdkVersion : String by extra
val androidTargetSdkVersion  : String by extra
val androidMinSdkVersion     : String by extra

plugins {
    println("*** Plugins evaluating ***")
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-platform-android")
    id("kotlinx-serialization")
}

val ccp = configurations.create("compileClasspath")
configurations.add(ccp)

android {
    buildToolsVersion = androidBuildToolsVersion

    compileSdkVersion(androidCompileSdkVersion.toInt())
    defaultConfig {
        multiDexEnabled = true
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
    }
    sourceSets {
        this["main"].java.srcDir("src/main/kotlin")
        this["test"].java.srcDir("src/test/kotlin")
        this["androidTest"].java.srcDir("src/androidTest/kotlin")
    }
    lintOptions {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }
}

val ktorClientAndroid        : String by extra
val ktorClientCio            : String by extra
val ktorClientJson           : String by extra

val kotlinXCoroutinesAndroid : String by extra
val androidXAppCompat        : String by extra

val jUnit                    : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

val androidXTestRunner       : String by extra
val androidXTestEspressoCore : String by extra

val frameworkAtribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

dependencies {

    implementation(clientCommonProject())
    implementation(sharedProject())

    // Kotlin Core
    implementation(kotlin("stdlib"))

    // Android
    implementation(kotlinXCoroutinesAndroid)
    implementation(androidXAppCompat)

    // Ktor
    implementation(ktorClientAndroid)
    implementation(ktorClientCio)
    implementation(ktorClientJson)

    // Testing
    testImplementation(jUnit)

    // Android Test
    androidTestImplementation(androidXTestRunner)
    androidTestImplementation(androidXTestEspressoCore)
}
