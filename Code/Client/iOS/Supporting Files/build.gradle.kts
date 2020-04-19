
buildscript {

    apply( from = "../../../shared.gradle.kts")

    val kotlinSerializationPlugin : String by extra
    val kotlinGradlePlugin        : String by extra

    val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
    repositories(configureSharedRepositories)

    dependencies {
        classpath(kotlinGradlePlugin)
        classpath(kotlinSerializationPlugin)
    }
}

plugins {
    id("com.android.library") apply false
}

val configureSharedRepositories = extra["configureSharedRepositories"] as RepositoryHandler.()->Unit
repositories(configureSharedRepositories)

allprojects {
    repositories(configureSharedRepositories)
}
