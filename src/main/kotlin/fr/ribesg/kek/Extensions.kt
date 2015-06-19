package fr.ribesg.kek

import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.glBegin
import org.lwjgl.opengl.GL11.glEnd

/**
 * @author Ribesg
 */

fun gl(mode: Int = GL_TRIANGLES, f: () -> Unit) {
    glBegin(mode)
    f()
    glEnd()
}
