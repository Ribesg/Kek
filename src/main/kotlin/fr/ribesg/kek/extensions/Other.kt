package fr.ribesg.kek.extensions

import java.nio.file.Path

/**
 * @author Ribesg
 */

fun res(path: Path): String
    = ClassLoader.getSystemResourceAsStream(path.toString()).reader().use { it.readText() }
