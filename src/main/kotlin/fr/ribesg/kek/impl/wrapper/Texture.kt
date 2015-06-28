package fr.ribesg.kek.impl.wrapper

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL14.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL21.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.opengl.GL31.*
import org.lwjgl.opengl.GL32.*
import org.lwjgl.opengl.GL33.*

/**
 * @author Ribesg
 */
public class Texture {

    public companion object {

        public fun unbind(): Unit
        = glBindTexture(GL_TEXTURE_2D, 0)

    }

    val id: Int = glGenTextures()

    public fun bind(): Unit
    = glBindTexture(GL_TEXTURE_2D, id)

    public fun setWrapMode(arg: Int) {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, arg)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, arg)
    }

    public fun setFilterMode(arg: Int) {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, arg)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, arg)
    }

}
