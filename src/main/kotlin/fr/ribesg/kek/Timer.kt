package fr.ribesg.kek

import org.lwjgl.glfw.GLFW

/**
 * @author Ribesg
 */
object Timer {


    var fps: Int = 0
        get() = if (fps > 0) fps else fpsCount

    private var lastLoopTime: Double = .0
    private var timeCount: Float = 0f
    private var fpsCount: Int = 0

    fun init() {
        lastLoopTime = getTime()
    }

    fun getTime(): Double = GLFW.glfwGetTime()


    fun getDelta(): Float {
        val time = getTime()
        val delta = (time - lastLoopTime).toFloat()
        lastLoopTime = time
        timeCount += delta
        return delta
    }

    fun update() {
        fpsCount++
        if (timeCount > 1f) {
            fps = fpsCount
            fpsCount = 0
            timeCount -= 1f
        }
    }
}
