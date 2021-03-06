import com.android.build.gradle.internal.coverage.JacocoReportTask

/**
 *
 * Build file for the 'Server' module of this Kotlin Multi-platform Application.
 *
 */

buildscript {

    apply(from = "../shared.gradle.kts")

    val kotlinVersion             : String by extra
    val androidGradlePlugin       : String by extra
    val kotlinSerializationPlugin : String by extra

    val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
    repositories(configureSharedRepositories)

    allprojects {
        repositories(configureSharedRepositories)
    }

    dependencies {
        // This should not be required as this is *not* an Android project.
        // Seems to be a current limitation of dependency on MPP project?
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(androidGradlePlugin)
        classpath(kotlinSerializationPlugin)
    }
}

val kotlinXSerializationRuntime : String by extra

val ktorServerServlet  : String by extra
val ktorServerNetty    : String by extra
val ktorServerCore     : String by extra
val ktorLocations      : String by extra
val ktorServerSessions : String by extra
val ktorAuth           : String by extra
val ktorWebsockets     : String by extra
val ktorSerialization  : String by extra

val logBackClassic     : String by extra

val ktorServerTests : String by extra

val sharedProject : ()->ProjectDependency by extra

val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
repositories(configureSharedRepositories)

allprojects {
    repositories(configureSharedRepositories)
}

plugins {
    application
    kotlin("jvm")
    war
    id("org.gretty") version "3.0.3"
    id("org.jetbrains.dokka") version "0.9.18"
    id("kotlinx-serialization") version "1.3.72"
    id("jacoco")
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

val frameworkAttribute = Attribute.of("org.chrishatton.example.framework", String::class.java)

configurations {
    val compileClasspath     by getting
    val testCompileClasspath by getting
    val runtimeClasspath     by getting
    val testRuntimeClasspath by getting

    listOf(compileClasspath,testCompileClasspath,runtimeClasspath,testRuntimeClasspath).forEach { configuration ->
        configuration.attributes { attribute(frameworkAttribute, "server") }
    }
}

dependencies {

    implementation(project(path = ":shared")) { attributes { attribute(frameworkAttribute, "server") } }

    implementation(kotlinXSerializationRuntime)

    implementation(ktorServerServlet)
    implementation(ktorServerNetty)
    implementation(ktorServerCore)
    implementation(ktorLocations)
    implementation(ktorServerSessions)
    implementation(ktorAuth)
    implementation(ktorWebsockets)
    implementation(ktorSerialization)
    implementation(logBackClassic)

    testCompile(ktorServerTests)
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = false
        csv.isEnabled = false
        html.destination = file("$buildDir/jacocoHtml")
    }
}

tasks["check"].dependsOn(tasks["jacocoTestReport"])

