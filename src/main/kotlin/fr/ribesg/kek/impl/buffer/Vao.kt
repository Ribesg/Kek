package fr.ribesg.kek.impl.buffer

import org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY
import org.lwjgl.opengl.GL11.glDisableClientState
import org.lwjgl.opengl.GL11.glEnableClientState
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glDeleteVertexArrays
import org.lwjgl.opengl.GL30.glGenVertexArrays

/**
 * @author Ribesg
 */
public class Vao {

    public companion object {

        public fun unbind(): Unit
            = glBindVertexArray(0)

    }

    val id: Int = glGenVertexArrays()

    public fun bind(): Unit
        = glBindVertexArray(id)

    public fun delete(): Unit
        = glDeleteVertexArrays(id)

}
