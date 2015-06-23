package fr.ribesg.kek.impl.shader

import fr.ribesg.kek.extensions.res
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER

/**
 * @author Ribesg
 */
public class Shader(type: Int, vararg path: String) {

    public companion object {

        /**
         * This "static" constructor guesses the type of Shader from the file extension.
         */
        public fun of(vararg path: String): Shader {
            val type = when {
                path.last().endsWith(".vert") -> GL_VERTEX_SHADER
                path.last().endsWith(".frag") -> GL_FRAGMENT_SHADER
                path.last().endsWith(".geom") -> GL_GEOMETRY_SHADER
                else                          ->
                    throw IllegalArgumentException("Path to invalid shader file: " + path)
            }
            return Shader(type, *path)
        }

    }

    val id: Int

    init {
        id = glCreateShader(type)
        val resPath = arrayOf("shaders", *path)
        glShaderSource(id, res(*resPath))
        glCompileShader(id)

        check(resPath)
    }

    public fun delete(): Unit
        = glDeleteShader(id)

    private fun check(path: Array<String>) {
        if (glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE) {
            throw RuntimeException("(while compiling $path): " + glGetShaderInfoLog(id))
        }
    }
}
