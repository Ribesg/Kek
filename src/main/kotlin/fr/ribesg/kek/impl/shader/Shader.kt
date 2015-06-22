package fr.ribesg.kek.impl.shader

import fr.ribesg.kek.extensions.res
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author Ribesg
 */
public class Shader(type: Int, path: Path) {

    public companion object {

        /**
         * This "static" constructor guesses the type of Shader from the file extension.
         */
        public fun of(path: Path): Shader {
            val type = when {
                path.toString().endsWith(".vert") -> GL_VERTEX_SHADER
                path.toString().endsWith(".frag") -> GL_FRAGMENT_SHADER
                path.toString().endsWith(".geom") -> GL_GEOMETRY_SHADER
                else                              ->
                    throw IllegalArgumentException("Path to invalid shader file: " + path)
            }
            return Shader(type, path)
        }

    }

    val id: Int

    init {
        id = glCreateShader(type)
        glShaderSource(id, res(Paths.get("shaders").resolve(path)))
        glCompileShader(id)

        check()
    }

    public fun delete(): Unit
        = glDeleteShader(id)

    private fun check() {
        if (glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE) {
            throw RuntimeException(glGetShaderInfoLog(id))
        }
    }
}
