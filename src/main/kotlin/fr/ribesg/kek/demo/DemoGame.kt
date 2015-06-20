package fr.ribesg.kek.demo

import fr.ribesg.kek.api.Config
import fr.ribesg.kek.api.Game
import fr.ribesg.kek.api.gfx.Entity
import fr.ribesg.kek.impl.buffer.Vao
import fr.ribesg.kek.impl.buffer.Vbo
import fr.ribesg.kek.impl.shader.Shader
import fr.ribesg.kek.impl.shader.ShaderProgram
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.glDrawArrays
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_STATIC_DRAW
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer

/**
 * A demonstration Game.
 *
 * @author Ribesg
 */
public class DemoGame : Game() {

    private class Triangle : Entity {

        private val vao: Vao
        private val shader: ShaderProgram

        init {
            vao = Vao()
            vao.bind()

            val vertexBuffer = BufferUtils.createFloatBuffer(2 * 3)
            vertexBuffer.put(floatArrayOf(-.5f, -.5f))
            vertexBuffer.put(floatArrayOf(.5f, -.5f))
            vertexBuffer.put(floatArrayOf(0f, .75f))
            vertexBuffer.flip()

            val vertexVbo = Vbo()
            vertexVbo.bind(GL_ARRAY_BUFFER)
            vertexVbo.data(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)
            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)
            glEnableVertexAttribArray(0)

            val colorBuffer = BufferUtils.createFloatBuffer(3 * 3)
            colorBuffer.put(floatArrayOf(1f, 0f, 0f))
            colorBuffer.put(floatArrayOf(0f, 1f, 0f))
            colorBuffer.put(floatArrayOf(0f, 0f, 1f))
            colorBuffer.flip()

            val colorVbo = Vbo()
            colorVbo.bind(GL_ARRAY_BUFFER)
            colorVbo.data(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW)
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0)
            glEnableVertexAttribArray(1)

            Vbo.unbind(GL_ARRAY_BUFFER)

            shader = ShaderProgram()
            shader.attach(Shader(GL_VERTEX_SHADER, "demo_vertex.glsl"))
            shader.attach(Shader(GL_FRAGMENT_SHADER, "demo_fragment.glsl"))
            shader.link()
        }

        override fun render() {
            shader.use()
            vao.bind()

            glDrawArrays(GL_TRIANGLES, 0, 2 * 3)

            Vao.unbind()
            ShaderProgram.useNone()
        }

        override fun free() {
            vao.delete()
            shader.delete()
        }
    }

    override fun configure() {
        Config.Window.BASE_TITLE = "DemoGame"
    }

    override fun init() {
        // Draw a rainbow triangle
        entities.add(Triangle())
        /*
        entities.add(object : Entity {
            override fun render() = gl {
                glColor3f(1f, 0f, 0f)
                glVertex2f(-.5f, -.5f)

                glColor3f(0f, 1f, 0f)
                glVertex2f(.5f, -.5f)

                glColor3f(0f, 0f, 1f)
                glVertex2f(0f, .75f)
            }
        })
        */

        // Draw a point
        /*
        entities.add(object : Entity {
            override fun render() {
                glPointSize(5f)

                gl(GL_POINTS) {
                    glColor3f(1f, 1f, 1f)
                    glVertex2f(0f, 0f)
                }
            }
        })
        */
    }

}
