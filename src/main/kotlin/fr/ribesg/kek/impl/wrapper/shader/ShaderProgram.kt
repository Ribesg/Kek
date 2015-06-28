package fr.ribesg.kek.impl.wrapper.shader

import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import java.util.LinkedList
import kotlin.properties.Delegates

/**
 * @author Ribesg
 */
public class ShaderProgram {

    public companion object {

        public val base: ShaderProgram by Delegates.lazy {
            ShaderProgram.of(
                Shader.of("base.vert"),
                Shader.of("base.frag")
            )
        }

        public fun of(vararg shaders: Shader): ShaderProgram {
            assert(shaders.isNotEmpty(), "A shader program is composed of at least one shader")
            val res = ShaderProgram()
            for (shader in shaders) {
                res.attach(shader)
            }
            res.link()
            return res
        }

        public fun useNone(): Unit
            = glUseProgram(0)

    }

    public val id: Int = glCreateProgram()
    private val shaders: MutableList<Shader> = LinkedList()

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
