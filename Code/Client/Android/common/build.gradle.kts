plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-platform-android")
    id("kotlinx-serialization")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        multiDexEnabled = true
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
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
}

val kotlin_version            : String by extra
val ktor_version              : String by extra

val ktorClientAndroid        : String by extra
val ktorClientCio            : String by extra
val ktorClientJson           : String by extra

val kotlinXCoroutinesAndroid : String by extra
val androidXAppCompat        : String by extra

val jUnit                    : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

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
}
