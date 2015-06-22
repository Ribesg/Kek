package fr.ribesg.kek.impl

import fr.ribesg.kek.api.Config
import fr.ribesg.kek.api.Game
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.glfw.GLFWvidmode
import org.lwjgl.opengl.GL11.*
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
        // Game configuration
        game.configure()

        // GLFW, LWJGL, OpenGL stuff
        errorCallback = Callbacks.errorCallbackPrint(System.err)
        glfwSetErrorCallback(errorCallback)

        if (glfwInit() != GL_TRUE) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)

        this.window = glfwCreateWindow(Config.Window.WIDTH, Config.Window.HEIGHT, Config.Window.BASE_TITLE, NULL, NULL)
        if (window == NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        keyCallback = object : GLFWKeyCallback() {
            override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, GL_TRUE)
                }
            }
        }
        glfwSetKeyCallback(window, keyCallback)

        val videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(videoMode) - Config.Window.WIDTH) / 2,
            (GLFWvidmode.height(videoMode) - Config.Window.HEIGHT) / 2
        );

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)

        glfwShowWindow(window)

        GLContext.createFromCurrent()

        // Timer and Game initialization
        Timer.init()
        game.init()
    }

    /**
     * Runs the main game loop.
     */
    fun loop() {

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        while (glfwWindowShouldClose(window) == GL_FALSE) {
            // Update game
            glfwPollEvents()
            game.update(Timer.getDelta())

            // FPS
            Timer.update()
            glfwSetWindowTitle(window, Config.Window.BASE_TITLE + " - FPS: " + Timer.fps)

            // Graphics
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
            game.render()
            glfwSwapBuffers(window)
        }
    }

    /**
     * Frees resources.
     */
    fun end() {
        game.end()

        try {
            // Destroy window
            glfwDestroyWindow(window)
            keyCallback.release()
        } finally {
            // Stop GLFW entirely
            glfwTerminate()
            errorCallback.release()
        }
    }

}
