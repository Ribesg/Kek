package fr.ribesg.kek.demo

import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.JarFile

object Main {

    init {
        // Get path from which we are running
        val path = this.javaClass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()

        val tmpDir: Path?
        if (path.endsWith(".jar")) {
            // Extract LWJGL3 natives to a temporary folder and use those
            tmpDir = Files.createTempDirectory("kek")
            println("Temporary directory created at $tmpDir")
            unzipNatives(JarFile(path), tmpDir)
            Runtime.getRuntime().addShutdownHook(Thread(Runnable {
                if (tmpDir != null) {
                    // FIXME Can't remove lwjgl.dll :(
                    if (!tmpDir.toFile().deleteRecursively()) {
                        println("Warning: failed to remove $tmpDir!")
                    } else {
                        println("Successfully removed temporary folder $tmpDir.")
                    }
                }
            }))
            System.setProperty("org.lwjgl.librarypath", tmpDir.resolve("natives").toAbsolutePath().toString())
            println("Running in jar mode")
        } else {
            // Seems that we're in a development context (in IDE or mvn exec), use repository natives
            tmpDir = null
            System.setProperty("org.lwjgl.librarypath", "lib/natives")
            println("Running in dev mode")
        }

        // Run
        try {
            DemoGame().run()
        } catch (satan: Throwable) {
            satan.printStackTrace()
        }
    }

    private fun unzipNatives(jarFile: JarFile, to: Path) {
        Files.createDirectory(to.resolve("natives"))
        jarFile.stream().filter { entry ->
            !entry.isDirectory() && !entry.getName().contains('/')
        }.forEach { entry ->
            jarFile.getInputStream(entry).use { inputStream ->
                println("\tExtracting " + entry.getName())
                Files.copy(inputStream, to.resolve("natives").resolve(entry.getName()))
            }
        }
    }

}

fun main(args: Array<String>): Unit {
    Main
}
