package fr.ribesg.kek.impl.util

import org.lwjgl.glfw.GLFW.glfwGetTime

/**
 * @author Ribesg
 */
object Timer {

    var fps: Int = 0
        get() = if (lastFpsCount > 0) lastFpsCount else fpsCount

    private var lastLoopTime: Double = .0
    private var timeCount: Float = 0f
    private var lastFpsCount: Int = 0
    private var fpsCount: Int = 0

    fun init() {
        lastLoopTime = getTime()
    }

    fun getTime(): Double
        = glfwGetTime()


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
            lastFpsCount = fpsCount
            fpsCount = 0
            timeCount -= 1f
        }
    }

}
