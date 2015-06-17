package fr.ribesg.kek

import org.lwjgl.opengl.GL11

/**
 * @author Ribesg
 */

fun gl(mode: Int, f: () -> Unit) {
    GL11.glBegin(mode)
    f()
    GL11.glEnd()
}
