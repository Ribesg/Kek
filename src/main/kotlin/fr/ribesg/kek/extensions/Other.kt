package fr.ribesg.kek.extensions

/**
 * @author Ribesg
 */

fun res(vararg path: String): String
    = ClassLoader.getSystemResourceAsStream(path.joinToString(separator = "/")).reader().use { it.readText() }