/**
 * The approach taken in this file is an attempt to maximise the efficiency of
 * building by a GradleBuild task-per-platform.
 *
 * That approach itself is known not to be the most efficient way of building
 * the entire project; since Gradle cannot consider the whole task-graph across
 * each GradleBuild invocation, leading to repeat builds of shared libraries.
 * The most efficient outcome would be to write an overarching Gradle project
 * which treats each Platform as a sub-project.
 * I experimented with this in a previous commit but it has complications and I
 * want to move ahead with other aspects of the Template development.
 * The best-compromise approach here collects all the tasks demanded of the
 * current whole-App build to at least ensure only one GradleBuild invocation per
 * Platform with, say, the tasks for testing and deploying a given platform
 * unified into it's own task graph.
 * This is particularly useful when on a Development machine I may wish to
 * invoke just the Unit Tests for a whole platform, while on a CI box (where
 * repeated overall Gradle invocations are costly due to Daemon startup and
 * Cache Storage/Retrieval) it is preferable to have build, test and deploy
 * all handled in a single Gradle invocation (albeit multi-GradleBuild).
 */

// The exclusion of 'lint' and 'lintVitalRelease' to disable lint are *not* desired.
// Currently necessary due to issue: https://youtrack.jetbrains.com/issue/KT-27170
// Re-enable lint when this issue is resolved.
enum class Platform(
    val identifier:String,
    val dirPath:String,
    vararg val excludedTaskNames: String
) {
    Android("Android","Client/Android", "lint","lintVitalRelease"),
    Desktop("Desktop","Client/Desktop"),
    Ios    ("Ios","Client/iOS/Supporting Files"),
    Server ("Server","Server")
}

/**
 * The Gradle task to invoke for testing of each of the Platform components
 */
val testTasks : Map<Platform,String> = mapOf(
    Platform.Android to "check",
    Platform.Desktop to "check",
    Platform.Ios     to "check",
    Platform.Server  to "check"
)

/**
 * The Gradle task to invoke for fully building deployable artifacts for each of the Platform components
 */
val buildTasks : Map<Platform,String> = mapOf(
    Platform.Android to "assemble",
    Platform.Desktop to "runtimeZip",
    Platform.Ios     to "build",
    Platform.Server  to "war"
)

/**
 * The Gradle task to invoke for fully cleaning deployable artifacts for each of the Platform components
 */
val cleanTasks : Map<Platform,String> = mapOf(
    Platform.Android to "clean",
    Platform.Desktop to "clean",
    Platform.Ios     to "clean",
    Platform.Server  to "clean"
)

/**
 * The Gradle task to invoke for deploying the artifacts produced by `buildTasks`.
 * Does not depend on buildTasks so assumes that buildTasks has been independently run
 * beforehand, leaving the artifacts available in the filesystem.
 */
val deployOnlyTasks : Map<Platform,String> = mapOf(
)

fun collectPlatformTaskNames(vararg platformTasksList: Map<Platform,String>) : Map<Platform,List<String>>
    = platformTasksList.map{it.entries}.reduce{a,b->a+b}.groupBy({it.key},{it.value})

fun registerPlatformTasks(name:String,appBuildTaskNames: Map<Platform,List<String>>) {
    val platformBuildTasks : List<GradleBuild> = appBuildTaskNames.mapNotNull { (platform,tasks) ->
        if(tasks.isEmpty()) return@mapNotNull null
        project.tasks.create(name+platform.identifier,GradleBuild::class) {
            this.dir = file(platform.dirPath)
            this.startParameter.setExcludedTaskNames(platform.excludedTaskNames.asIterable())
            this.tasks = tasks
        }
    }
    project.tasks.register(name,Task::class) {
        setDependsOn(platformBuildTasks)
    }
}

registerPlatformTasks("buildApp",        collectPlatformTaskNames(buildTasks))
registerPlatformTasks("testApp",         collectPlatformTaskNames(testTasks))
registerPlatformTasks("buildAndTestApp", collectPlatformTaskNames(buildTasks,testTasks))
registerPlatformTasks("deployOnlyApp",   collectPlatformTaskNames(deployOnlyTasks))
registerPlatformTasks("cleanApp",        collectPlatformTaskNames(cleanTasks))

tasks {

    register("publishMultiMvpLib",GradleBuild::class) {
        this.dir = file("Lib/multi-mvp")
        this.tasks = listOf("publish")
    }

    register("publishCoroutinesUiLib",GradleBuild::class) {
        this.dir = file("Lib/coroutines-ui")
        this.tasks = listOf("publish")
    }

    register("publishLibs") {
        dependsOn("publishMultiMvpLib", "publishCoroutinesUiLib")
    }
}

