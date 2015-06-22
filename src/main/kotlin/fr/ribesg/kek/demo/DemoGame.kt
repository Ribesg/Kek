package fr.ribesg.kek.demo

import fr.ribesg.kek.api.Config
import fr.ribesg.kek.api.Game
import fr.ribesg.kek.api.gfx.Entity
import fr.ribesg.kek.impl.buffer.Vao
import fr.ribesg.kek.impl.buffer.Vbo
import fr.ribesg.kek.impl.shader.Shader
import fr.ribesg.kek.impl.shader.ShaderProgram
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL11.GL_LINES
import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.glDrawArrays
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
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

            val vertexVbo = Vbo.of(
                0f, .5f,
                -.43f, -.25f,
                .43f, -.25f
            )

            val colorVbo = Vbo.of(
                1f, 0f, 0f,
                0f, 1f, 0f,
                0f, 0f, 1f
            )

            vao = Vao()
            vao.bind()

            glEnableVertexAttribArray(0)
            vertexVbo.bind(GL_ARRAY_BUFFER)
            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)

            glEnableVertexAttribArray(1)
            colorVbo.bind(GL_ARRAY_BUFFER)
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0)

            Vao.unbind()

            shader = ShaderProgram.of(
                Shader.of("demo", "rotate_180.vert"),
                Shader.of("demo", "invert_colors_and_tweak_alpha.frag")
            )
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

    private class Axis : Entity {

        private val vao: Vao

        init {
            val axisVbo = Vbo.of(
                0f, 0f,
                .8f, 0f,
                0f, 0f,
                0f, .8f
            )

            val axisColorVbo = Vbo.of(
                1f, 0f, 0f,
                1f, 0f, 0f,

                0f, 1f, 0f,
                0f, 1f, 0f
            )

            vao = Vao()
            vao.bind()

            glEnableVertexAttribArray(0)
            axisVbo.bind(GL_ARRAY_BUFFER)
            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)

            glEnableVertexAttribArray(1)
            axisColorVbo.bind(GL_ARRAY_BUFFER)
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0)

            ShaderProgram.base
        }

        override fun render() {
            ShaderProgram.base.use()
            vao.bind()

            glDrawArrays(GL_LINES, 0, 2 * 2)

            Vao.unbind()
            ShaderProgram.useNone()
        }
    }

    override fun configure() {
        Config.Window.WIDTH = 768
        Config.Window.HEIGHT = 768
        Config.Window.BASE_TITLE = "DemoGame"
    }

    override fun init() {
        // Draw a rainbow triangle
        entities.add(Triangle())

        // Draw some axies to understand wtf is going on
        entities.add(Axis())
    }

}
