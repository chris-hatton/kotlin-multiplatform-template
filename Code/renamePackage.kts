
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.exitProcess

fun printSeparator() = println("--------")

println("Multiplatform Template Package Renamer")
println("Chris Hatton (christopherhattonuk@gmail.com)")
println("https://github.com/chris-hatton/kotlin-multiplatform-template")
printSeparator()
println("This script is intended only for initial setup of the Multiplatform template,")
println("to rename the numerous package paths, to suit your application.")
printSeparator()

val usage = """
Script to rename the 'Example' Kotlin package-path throughout the project,
to the actual desired path for your project, after cloning.
Uses the Git `mv` and `rm` commands to ensure the rename is tracked
"""

val argsList  : List<String> = args.toList()
val toPackage : String       = args.firstOrNull { !it.startsWith("--") } ?: { println(usage); exitProcess(1) }()
val isDryRun  : Boolean      = argsList.contains("--dry-run") || argsList.contains("/d")
val isNoGit   : Boolean      = argsList.contains("--no-git" ) || argsList.contains("/ng")

val runtime          : Runtime = Runtime.getRuntime()
val packageDelimeter : Char = '.'
val pathDelimeter    : String = File.separator

val dirPrefix   = "kotlin"
val fromPackage = "org.chrishatton.example"

println("From package: ${fromPackage}")
println("To   package: ${toPackage}")
printSeparator()

val dirPrefixPath = dirPrefix.split(pathDelimeter)
val fromPath : List<String> = dirPrefixPath + fromPackage.split(packageDelimeter)
val toPath   : List<String> = dirPrefixPath + toPackage.split(packageDelimeter)

println("From path: ${fromPath.joinToString(File.separator)}")
println("To   path: ${toPath.joinToString(File.separator)}")

// Portion of the path that will actually have to be renamed: Don't bother renaming a matching path-prefix
val zipPath    = fromPath.zip(toPath)
val renamePath = zipPath.dropWhile { it.first == it.second }
val offset     = zipPath.count     { it.first == it.second }

println("Will start renaming from $offset components into a matched path")
println("Will perform direct renames of the following path components:")
renamePath.forEach { println("'${it.first}' to '${it.second}'") }

interface Operator {
    fun move(src: File, dest: File) : Boolean
    fun delete(file: File) : Boolean
    fun makeDir(dir:File) : Boolean
}

object DryRunOperator : Operator {
    override fun move(src: File, dest: File) : Boolean { println("Move '${src.path}' to '${dest.path}'"); return true }
    override fun delete(file: File)          : Boolean { println("Delete '${file.path}'"); return true }
    override fun makeDir(dir: File)          : Boolean { println("Make Dir: '${dir.path}'"); return true }
}

object JavaOperator : Operator {
    override fun move(src: File, dest: File) : Boolean = src.renameTo(dest)
    override fun delete(file: File)          : Boolean = file.delete()
    override fun makeDir(dir:File)           : Boolean = dir.mkdir()
}

object GitOperator : Operator {
    override fun move(src: File, dest: File) : Boolean = gitProcess("mv",src.path,dest.path)
    override fun delete(file: File)          : Boolean = gitProcess("rm","-r","-f",file.path)
    override fun makeDir(dir:File)           : Boolean = gitProcess("mkdir",dir.path)

    private fun gitProcess(vararg gitArgs:String) : Boolean {
        val procArgs = listOf("git") + gitArgs.toList()
        return ProcessBuilder(procArgs)
            .inheritIO()
            .start()
            .waitFor() == 0
    }
}

sealed class PathSizing {
    // Before moving, it will be necessary to create new path components
    data class Extend(val pathComponents: List<String>) : PathSizing()
    // It's only necessary to rename the existing path components: there are no greater or fewer
    object Rename : PathSizing()
    // After moving, it will be necessary to remove extraneous path components
    data class Shrink(val pathComponentCount: Int) : PathSizing()
}

val pathSizing : PathSizing = when {
    fromPath.size <  toPath.size -> PathSizing.Extend(pathComponents = toPath.subList(fromPath.size,toPath.size))
    fromPath.size >  toPath.size -> PathSizing.Shrink(pathComponentCount = fromPath.size - toPath.size)
    else -> PathSizing.Rename
}

println("Dry Run? $isDryRun")
println("Path Sizing: $pathSizing")

val operator = when {
    isDryRun -> DryRunOperator
    isNoGit  -> JavaOperator
    else     -> GitOperator
}

/**
 * Recursive function to search for the kotlin package folder path
 * throughout the project and rename appropriately.
 */
fun rename(file: File = File("."), componentIndex: Int = 0) : Boolean {

    val isMatch = fromPath[componentIndex] == file.name
    val nextComponentIndex = if(isMatch) componentIndex + 1 else 0

    val isCompleteMatch = (isMatch && nextComponentIndex == fromPath.size)

    if(isCompleteMatch)
    {
        printSeparator()
        println("Found path: ${file.path}")
        val filesToMove = file.listFiles()
        val relativePath : String? = when(pathSizing) {
            is PathSizing.Extend -> pathSizing.pathComponents.fold(".") { current,new ->
                val relativePath = current + File.separator + new
                operator.makeDir(dir = file.resolve(relativePath))
                relativePath
            }
            is PathSizing.Rename -> null
            is PathSizing.Shrink -> "..${File.separator}".repeat(pathSizing.pathComponentCount)
        }
        relativePath?.let {
            // Move files to the desination path
            filesToMove.forEach { moveFile ->
                operator.move(src = moveFile, dest = moveFile.resolveSibling(relativePath))
            }
        }
    }

    val wasCompleteMatch = isCompleteMatch || file.listFiles()?.fold(false) { initial,childFile ->
        val wasComplete = childFile.isDirectory && rename(childFile,nextComponentIndex)
        if(wasComplete && componentIndex > (toPath.size-2)) {
            operator.delete(file = childFile)
        }
        wasComplete || initial
    } ?: false

    if(wasCompleteMatch && componentIndex-offset in 0 until renamePath.size) {
        val newFileName : String = renamePath[componentIndex-offset].second
        operator.move(src = file, dest = file.resolveSibling(newFileName))
    }

    return wasCompleteMatch
}

val wasCompleteMatch = rename() // Fly, my pretty!

val summary = if(wasCompleteMatch) {
    "Changes made successfully."
} else {
    "No changes made: no directory path matches for '${fromPath.joinToString(File.separator)}' found."
}

printSeparator()
println(summary)
printSeparator()