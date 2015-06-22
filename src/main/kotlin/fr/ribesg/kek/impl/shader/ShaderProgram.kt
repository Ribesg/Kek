package fr.ribesg.kek.impl.shader

import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import java.nio.file.Path
import java.util.LinkedList

/**
 * @author Ribesg
 */
public class ShaderProgram {

    public companion object {

        public fun of(vararg shaders: Path): ShaderProgram {
            assert(shaders.isNotEmpty(), "A shader program is composed of at least one shader")
            val res = ShaderProgram()
            for (p in shaders) {
                res.attach(Shader.of(p))
            }
            res.link()
            return res
        }

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
        shaders.clear()
        glDeleteProgram(id)
    }

    private fun check() {
        if (glGetProgrami(id, GL_LINK_STATUS) != GL_TRUE) {
            throw RuntimeException(glGetProgramInfoLog(id))
        }
    }
}
