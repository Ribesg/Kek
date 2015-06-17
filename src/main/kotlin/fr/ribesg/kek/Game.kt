package fr.ribesg.kek

import org.lwjgl.glfw.*
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GLContext
import org.lwjgl.system.MemoryUtil.NULL
import kotlin.properties.Delegates

class Game {

    var errorCallback: GLFWErrorCallback by Delegates.notNull()
    var keyCallback: GLFWKeyCallback by Delegates.notNull()

    var window: Long by Delegates.notNull()

    fun run() {
        init()
        loop()
        dispose()
    }

    fun init() {
        errorCallback = Callbacks.errorCallbackPrint(System.err)
        GLFW.glfwSetErrorCallback(errorCallback)

        if (GLFW.glfwInit() != GL11.GL_TRUE) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE)

        this.window = GLFW.glfwCreateWindow(Screen.WIDTH, Screen.HEIGHT, Screen.TITLE, NULL, NULL)
        if (window == NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        keyCallback = object : GLFWKeyCallback() {
            override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                    GLFW.glfwSetWindowShouldClose(window, GL11.GL_TRUE)
                }
            }
        }
        GLFW.glfwSetKeyCallback(window, keyCallback)

        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
        GLFW.glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - Screen.WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - Screen.HEIGHT) / 2
        );

        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(1)

        GLFW.glfwShowWindow(window)

        Timer.init()
    }

    fun loop() {
        GLContext.createFromCurrent()

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        GL11.glPointSize(5f)

        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            val delta = Timer.getDelta()

            // Graphics
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
            gl {
                GL11.glColor3f(1f, 0f, 0f)
                GL11.glVertex2f(-.5f, -.5f)

                GL11.glColor3f(0f, 1f, 0f)
                GL11.glVertex2f(.5f, -.5f)

                GL11.glColor3f(0f, 0f, 1f)
                GL11.glVertex2f(0f, .75f)
            }
            gl(GL11.GL_POINTS) {
                GL11.glColor3f(1f, 1f, 1f)
                GL11.glVertex2f(0f, 0f)
            }
            GLFW.glfwSwapBuffers(window)

            // FPS
            Timer.update()
            GLFW.glfwSetWindowTitle(window, Screen.TITLE + " - FPS: " + Timer.fps)

            // Input
            GLFW.glfwPollEvents()
        }
    }

    fun dispose() {
        try {
            GLFW.glfwDestroyWindow(window)
            keyCallback.release()
        } finally {
            GLFW.glfwTerminate()
            errorCallback.release()
        }
    }

}
