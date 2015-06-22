package fr.ribesg.kek.extensions

import org.lwjgl.opengl.GL11

/**
 * @author Ribesg
 */

fun gl(mode: Int = GL11.GL_TRIANGLES, f: () -> Unit) {
    GL11.glBegin(mode)
    f()
    GL11.glEnd()
}
