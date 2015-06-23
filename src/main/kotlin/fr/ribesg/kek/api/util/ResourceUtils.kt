package fr.ribesg.kek.api.util

/**
 * @author Ribesg
 */
public object ResourceUtils {

    public val shadersRoot: String = "shaders"

    public fun getShader(path: Array<out String>): String
        = getRes(shadersRoot, path)

    private fun getRes(root: String, path: Array<out String>): String {
        val classLoader = this.javaClass.getClassLoader()
        val fullPath = "$root/" + path.joinToString("/")
        val inputStream = classLoader.getResourceAsStream(fullPath)
        return inputStream.reader().use { it.readText() }
    }

}
