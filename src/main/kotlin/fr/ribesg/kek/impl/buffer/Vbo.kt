package fr.ribesg.kek.impl.buffer

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15.*
import java.nio.FloatBuffer

/**
 * @author Ribesg
 */
public class Vbo {

    public companion object {

        public fun of(vararg values: Float): Vbo {
            val buf = BufferUtils.createFloatBuffer(values.size())
            buf.put(values)
            buf.flip()

            val vbo = Vbo()
            vbo.bind(GL_ARRAY_BUFFER)
            vbo.data(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW)
            Vbo.unbind(GL_ARRAY_BUFFER)

            return vbo
        }

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
