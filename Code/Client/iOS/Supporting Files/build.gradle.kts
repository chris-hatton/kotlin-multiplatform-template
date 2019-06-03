
allprojects {

    apply {
        from("${rootProject.projectDir}/common.gradle.kts")
    }

    repositories {
        google()
        jcenter()
        maven( url = "https://kotlin.bintray.com/kotlinx" )
        maven( url = "https://kotlin.bintray.com/kotlin/ktor" )
    }
}
