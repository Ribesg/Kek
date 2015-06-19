package fr.ribesg.kek.impl

import fr.ribesg.kek.api.Config
import fr.ribesg.kek.api.Game
import org.lwjgl.glfw.*
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GLContext
import org.lwjgl.system.MemoryUtil.NULL
import kotlin.properties.Delegates

/**
 * The engine's Core.
 *
 * @author Ribesg
 */
object Core {

    // GLFW Callbacks
    private var errorCallback: GLFWErrorCallback by Delegates.notNull()
    private var keyCallback: GLFWKeyCallback by Delegates.notNull()

    // Our Window
    private var window: Long by Delegates.notNull()

    // The Game
    private var game: Game by Delegates.notNull()

    /**
     * Called by [Game.run()], runs [init()], [loop()] then [end()].
     */
    fun run(game: Game) {
        this.game = game

        init()
        loop()
        end()
    }

    /**
     * Initializes the Core.
     */
    fun init() {
        // GLFW, LWJGL, OpenGL stuff
        errorCallback = Callbacks.errorCallbackPrint(System.err)
        GLFW.glfwSetErrorCallback(errorCallback)

        if (GLFW.glfwInit() != GL11.GL_TRUE) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE)

        this.window = GLFW.glfwCreateWindow(Config.Screen.WIDTH, Config.Screen.HEIGHT, Config.Screen.BASE_TITLE, NULL, NULL)
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

        val videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
        GLFW.glfwSetWindowPos(
            window,
            (GLFWvidmode.width(videoMode) - Config.Screen.WIDTH) / 2,
            (GLFWvidmode.height(videoMode) - Config.Screen.HEIGHT) / 2
        );

        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(1)

        GLFW.glfwShowWindow(window)

        // Timer and Game initialization
        Timer.init()
        game.init()
    }

    /**
     * Runs the main game loop.
     */
    fun loop() {
        GLContext.createFromCurrent()

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
            // Update game
            GLFW.glfwPollEvents()
            game.update(Timer.getDelta())

            // FPS
            Timer.update()
            GLFW.glfwSetWindowTitle(window, Config.Screen.BASE_TITLE + " - FPS: " + Timer.fps)

            // Graphics
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
            game.render()
            GLFW.glfwSwapBuffers(window)
        }
    }

    /**
     * Frees resources.
     */
    fun end() {
        game.end()

        try {
            // Destroy window
            GLFW.glfwDestroyWindow(window)
            keyCallback.release()
        } finally {
            // Stop GLFW entirely
            GLFW.glfwTerminate()
            errorCallback.release()
        }
    }

}
