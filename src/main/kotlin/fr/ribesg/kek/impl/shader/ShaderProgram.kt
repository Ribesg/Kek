package fr.ribesg.kek.impl.shader

import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import java.util.LinkedList

/**
 * @author Ribesg
 */
public class ShaderProgram {

    public companion object {

        public fun useNone(): Unit
            = glUseProgram(0)

    }

    val id: Int = glCreateProgram()
    val shaders: MutableList<Shader> = LinkedList()

    public fun attach(s: Shader): Unit {
        glAttachShader(id, s.id)
        shaders.add(s)
    }

    public fun link() {
        glLinkProgram(id)
        check()
    }

    public fun use(): Unit
        = glUseProgram(id)

    public fun delete(): Unit {
        glUseProgram(0)
        shaders.forEach { s ->
            glDetachShader(id, s.id)
            s.delete()
        }
        glDeleteProgram(id)
    }

    private fun check() {
        if (glGetProgrami(id, GL_LINK_STATUS) != GL_TRUE) {
            throw RuntimeException(glGetProgramInfoLog(id))
        }
    }
}
