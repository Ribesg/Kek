package fr.ribesg.kek.impl.buffer

import org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY
import org.lwjgl.opengl.GL11.glDisableClientState
import org.lwjgl.opengl.GL11.glEnableClientState
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL15.glBufferData
import org.lwjgl.opengl.GL15.glDeleteBuffers
import org.lwjgl.opengl.GL15.glGenBuffers
import java.nio.FloatBuffer

/**
 * @author Ribesg
 */
public class Vbo {

    public companion object {

        public fun enable(): Unit
            = glEnableClientState(GL_VERTEX_ARRAY)

        public fun disable(): Unit
            = glDisableClientState(GL_VERTEX_ARRAY)

        public fun unbind(to: Int): Unit
            = glBindBuffer(to, 0)

    }

    val id: Int = glGenBuffers()

    public fun bind(to: Int): Unit
        = glBindBuffer(to, id)

    public fun data(to: Int, buf: FloatBuffer, use: Int): Unit
        = glBufferData(to, buf, use)

    public fun delete(): Unit
        = glDeleteBuffers(id)

}
