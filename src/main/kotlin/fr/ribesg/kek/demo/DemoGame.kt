package fr.ribesg.kek.demo

import fr.ribesg.kek.api.Config
import fr.ribesg.kek.api.Game
import fr.ribesg.kek.api.gfx.Entity
import fr.ribesg.kek.gl
import org.lwjgl.opengl.GL11

/**
 * A demonstration Game.
 *
 * @author Ribesg
 */
public class DemoGame : Game() {

    override fun configure() {
        Config.Window.BASE_TITLE = "DemoGame"
    }

    override fun init() {
        // Draw a rainbow triangle
        entities.add(object : Entity {
            override fun render() = gl {
                GL11.glColor3f(1f, 0f, 0f)
                GL11.glVertex2f(-.5f, -.5f)

                GL11.glColor3f(0f, 1f, 0f)
                GL11.glVertex2f(.5f, -.5f)

                GL11.glColor3f(0f, 0f, 1f)
                GL11.glVertex2f(0f, .75f)
            }
        })

        // Draw a point
        entities.add(object : Entity {
            override fun render() {
                GL11.glPointSize(5f)

                gl(GL11.GL_POINTS) {
                    GL11.glColor3f(1f, 1f, 1f)
                    GL11.glVertex2f(0f, 0f)
                }
            }
        })
    }

}
