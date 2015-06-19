package fr.ribesg.kek.demo

fun main(args: Array<String>) {
    // Set path to LWJGL3 natives
    System.setProperty("org.lwjgl.librarypath", "lwjgl3/lwjgl/native")

    // Run
    try {
        DemoGame().run()
    } catch (satan: Throwable) {
        satan.printStackTrace()
        System.exit(42)
    } finally {
        System.exit(0)
    }
}
