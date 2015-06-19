package fr.ribesg.kek.impl.shader

import fr.ribesg.kek.res
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import java.nio.file.Paths

/**
 * @author Ribesg
 */
public class Shader(type: Int, name: String) {

    val id: Int

    init {
        id = glCreateShader(type)
        glShaderSource(id, res(Paths.get("shaders", name)))
        glCompileShader(id)

        check()
    }

    public fun delete(): Unit = glDeleteShader(id)

    private fun check() {
        if (glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE) {
            throw RuntimeException(glGetShaderInfoLog(id))
        }
    }
}
