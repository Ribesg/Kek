package fr.ribesg.kek

import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.glBegin
import org.lwjgl.opengl.GL11.glEnd
import java.nio.file.Path

/**
 * @author Ribesg
 */

fun gl(mode: Int = GL_TRIANGLES, f: () -> Unit) {
    glBegin(mode)
    f()
    glEnd()
}

fun res(path: Path): String
    = ClassLoader.getSystemResourceAsStream(path.toString()).reader().use { it.readText() }
