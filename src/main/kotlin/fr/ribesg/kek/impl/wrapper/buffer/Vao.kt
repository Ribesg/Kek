package fr.ribesg.kek.impl.wrapper.buffer

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

    public val id: Int = glGenVertexArrays()

    public fun bind(): Unit
        = glBindVertexArray(id)

    public fun delete(): Unit
        = glDeleteVertexArrays(id)

}
