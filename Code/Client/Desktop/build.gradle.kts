/**
 *
 * Build file for the 'JavaFX Desktop' module of this Kotlin Multi-platform Application.
 *
 */

import de.dynamicfiles.projects.gradle.plugins.javafx.JavaFXGradlePluginExtension

buildscript {

    apply( from = "common.gradle.kts")

    val kotlinVersion             : String by extra
    val kotlinSerializationPlugin : String by extra

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
        maven {
            setUrl("http://sandec.bintray.com/repo")
        }
    }

    dependencies {
        classpath("de.dynamicfiles.projects.gradle.plugins:javafx-gradle-plugin:8.8.2")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlinSerializationPlugin)
    }
}

val kotlinVersion           : String by extra
val kotlinCoroutinesVersion : String by extra
val ktorVersion             : String by extra
val tornadoFxVersion        : String by extra
val kotlinStandardLibrary7  : String by extra
val javaFxBase              : String by extra
val javaFxGraphics          : String by extra
val javaFxControls          : String by extra
val javaFxFxml              : String by extra

val kotlinXCoroutinesCore   : String by extra
val kotlinXCoroutinesJavaFx : String by extra
val tornadoFx               : String by extra
val ktorClient              : String by extra
val ktorClientCio           : String by extra
val ktorClientJson          : String by extra

val jUnit : String by extra

val clientCommonProject : ()->ProjectDependency by extra
val sharedProject       : ()->ProjectDependency by extra

//val moduleName = "exampleApp"

apply( plugin = "javafx-gradle-plugin" )

plugins {
    val kotlinVersion = "1.3.40"
    id("application")
    kotlin("jvm" ) version kotlinVersion
    id("kotlinx-serialization") version kotlinVersion
}

val myMainClassName = "org.chrishatton.example.ExampleApp"
val myVendor = "org.chrishatton"

extensions.findByType<JavaFXGradlePluginExtension>()!!.apply {

    vendor    = myVendor

    isVerbose = true
    mainClass = myMainClassName
    jfxAppOutputDir = "build/jfx/app"
    jfxMainAppJarName = "project-jfx.jar"
    deployDir = "src/main/deploy"
    isUseEnvironmentRelativeExecutables = true
    libFolderName = "lib"

    // gradle jfxJar
    isCss2bin = false
    preLoader = null // String
    isUpdateExistingJar = false
    isAllPermissions = false
    manifestAttributes = null // Map<String, String>
    isAddPackagerJar = true
    isCopyAdditionalAppResourcesToJar = false
    isSkipCopyingDependencies = false
    isUseLibFolderContentForManifestClasspath = false
    fixedManifestClasspath = null

    // gradle jfxNative
    identifier = null  // String - setting this for windows-bundlers makes it possible to generate upgradeable installers (using same GUID)
    nativeOutputDir = "build/jfx/native"
    bundler = "ALL" // set this to some specific, if your don't want all bundlers running, examples "windows.app", "jnlp", ...
    jvmProperties = null // Map<String, String>
    jvmArgs = null // List<String>
    userJvmArgs = null // Map<String, String>
    launcherArguments = null // List<String>
    nativeReleaseVersion = "1.0"
    isNeedShortcut = false
    isNeedMenu = false
    bundleArguments = mapOf(
        // dont bundle JRE (not recommended, but increases build-size/-speed)
        //runtime to null
    )
    appName = "project" // this is used for files below "src/main/deploy", e.g. "src/main/deploy/package/windows/project.ico"
    additionalBundlerResources = null // path to some additional resources for the bundlers when creating application-bundle
    additionalAppResources = null // path to some additional resources when creating application-bundle
    //secondaryLaunchers = listOf(mapOf(appName to "somethingDifferent"), mapOf(appName to "somethingDifferent2"))
    fileAssociations = null // List<Map<String, Object>>
    isNoBlobSigning = false // when using bundler "jnlp", you can choose to NOT use blob signing
    customBundlers = null // List<String>
    isFailOnError = false
    isOnlyCustomBundlers = false
    isSkipJNLP = false
    isSkipNativeVersionNumberSanitizing = false // anything than numbers or dots are removed
    additionalJarsignerParameters = null // List<String>
    isSkipMainClassScanning = false // set to true might increase build-speed

    isSkipNativeLauncherWorkaround124 = false
    isSkipNativeLauncherWorkaround167 = false
    isSkipNativeLauncherWorkaround205 = false
    isSkipJNLPRessourcePathWorkaround182 = false
    isSkipSigningJarFilesJNLP185 = false
    isSkipSizeRecalculationForJNLP185 = false
    isSkipMacBundlerWorkaround = false

    // gradle jfxRun
    runJavaParameter = null // String
    runAppParameter = null // String

    // per default the outcome of the gradle "jarTask" will be used, set this to specify otherwise (like proguard-output)
    alternativePathToJarFile = null // String

    // to disable patching of ant-javafx.jar, set this to false
    isUsePatchedJFXAntLib = true

    // making it able to support absolute paths, defaults to "false" for maintaining old behaviour
    isCheckForAbsolutePaths = false

    // gradle jfxGenerateKeyStore
    keyStore = "src/main/deploy/keystore.jks"
    keyStoreAlias = "myalias"
    keyStorePassword = "password"
    keyPassword = null // will default to keyStorePassword
    keyStoreType = "jks"
    isOverwriteKeyStore = false

    certDomain = null // required
    certOrgUnit = null // defaults to "none"
    certOrg = null // required
    certState = null // required
    certCountry = null // required
}

application {
    mainClassName = myMainClassName
}

tasks.withType<JavaCompile> {

    group   = "org.chrishatton.example"
    version = "1.0"

    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()

}

repositories {
    google()
    jcenter()
    maven( url = "https://kotlin.bintray.com/kotlinx" )
    maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
}

dependencies {

    implementation(clientCommonProject())
    implementation(sharedProject())

    implementation(kotlinStandardLibrary7)

    // Kotlin Core
    implementation(kotlinXCoroutinesCore)
    implementation(kotlinXCoroutinesJavaFx)

    implementation(tornadoFx)

    // Ktor
    implementation(ktorClient)
    implementation(ktorClientCio)
    implementation(ktorClientJson)

    testImplementation(jUnit)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
