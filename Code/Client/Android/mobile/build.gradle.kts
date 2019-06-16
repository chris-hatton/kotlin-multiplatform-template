
repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-platform-android")
    id("kotlinx-serialization")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        multiDexEnabled = true
        applicationId = "org.chrishatton.projectclient"
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

val kotlin_version            : String = extra["kotlin_version"].toString()
val kotlin_coroutines_version : String = extra["kotlin_coroutines_version"].toString()
val ktor_version              : String = extra["ktor_version"].toString()

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.0.1")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")

    // Project
    project(":common")
    project(":shared")

    // Kotlin Core
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines_version")

    // Ktor
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")

    // Android
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    androidTestImplementation("com.android.support.test:runner:1.0.2")

//    testImplementation(kotlin("test"))
//    testImplementation(kotlin("test-junit"))
}
