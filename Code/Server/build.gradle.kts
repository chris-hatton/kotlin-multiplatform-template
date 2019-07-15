
/**
 *
 * Build file for the 'Server' module of this Kotlin Multi-platform Application.
 *
 */

buildscript {

    apply(from = "$rootDir/common.gradle.kts")

    val androidGradlePlugin       : String by extra
    val kotlinSerializationPlugin : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
    }
    dependencies {
        // This should not be required as this is *not* an Android project.
        // Seems to be a current limitation of dependency on MPP project?
        classpath(androidGradlePlugin)
        classpath(kotlinSerializationPlugin)
    }
}

val kotlinXSerializationRuntimeJvm : String by extra
val kotlinStandardLibrary8         : String by extra

val ktorServerServlet  : String by extra
val ktorServerNetty    : String by extra
val ktorServerCore     : String by extra
val ktorLocations      : String by extra
val ktorServerSessions : String by extra
val ktorAuth           : String by extra
val ktorWebsockets     : String by extra
val ktorGson           : String by extra
val logBackClassic     : String by extra

val ktorServerTests : String by extra

val sharedProject : ()->ProjectDependency by extra

allprojects.forEach {
    repositories {
        mavenLocal()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
    }
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    maven { url = uri("https://kotlin.bintray.com/kotlinx") }
}

plugins {
    application
    kotlin("jvm") version "1.3.40"
    war
    id("org.gretty") version "2.2.0"
    id("org.jetbrains.dokka") version "0.9.18"
    id("kotlinx-serialization") version "1.3.40"
}

group   = "Server"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

gretty {
    webXml = "web.xml"
    contextPath = "/"
    logbackConfigFile = "resources/logback.xml"
}

war {
    webAppDirName = "webapp"
}

tasks.dokka {
    moduleName      = "server"
    outputFormat    = "html"
    outputDirectory = "$buildDir/javadoc"
}

dependencies {

    implementation(sharedProject())

    implementation(kotlinXSerializationRuntimeJvm)
    implementation(kotlinStandardLibrary8)

    implementation(ktorServerServlet)
    implementation(ktorServerNetty)
    implementation(ktorServerCore)
    implementation(ktorLocations)
    implementation(ktorServerSessions)
    implementation(ktorAuth)
    implementation(ktorWebsockets)
    implementation(ktorGson)
    implementation(logBackClassic)

    testCompile(ktorServerTests)
}

