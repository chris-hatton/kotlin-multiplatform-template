
apply {
    from("$rootDir/common.gradle.kts")
}

val ktor_version                 : String by project
val kotlin_version               : String by project
val logback_version              : String by project
val kotlin_serialization_version : String by project

project.buildscript {
    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
    }
    dependencies {
        // This should not be required - this is *not* an Android project.
        // Seems to be a current limitation of dependency on MPP project?
        classpath("com.android.tools.build:gradle:3.4.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.40")
    }
}

project.allprojects.forEach {
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

group = "Server"
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
    moduleName = "server"
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

dependencies {

    compile(project(":shared"))

    compile("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$kotlin_serialization_version")

    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compile("io.ktor:ktor-server-servlet:$ktor_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-locations:$ktor_version")
    compile("io.ktor:ktor-server-sessions:$ktor_version")
    compile("io.ktor:ktor-auth:$ktor_version")
//    compile("io.ktor:ktor-client-core:$ktor_version")
//    compile("io.ktor:ktor-client-core-jvm:$ktor_version")
//    compile("io.ktor:ktor-client-jetty:$ktor_version")
//    compile("io.ktor:ktor-client-json-jvm:$ktor_version")
//    compile("io.ktor:ktor-client-gson:$ktor_version")
//    compile("io.ktor:ktor-client-cio:$ktor_version")
    compile("io.ktor:ktor-websockets:$ktor_version")
//    compile("io.ktor:ktor-client-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")

    testCompile("io.ktor:ktor-server-tests:$ktor_version")
}

