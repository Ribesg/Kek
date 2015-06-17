package fr.ribesg.kek

fun main(args: Array<String>) {
    System.setProperty("org.lwjgl.librarypath", "lwjgl3/lwjgl/native")
    try {
        Game().run()
    } catch (satan: Throwable) {
        satan.printStackTrace()
        System.exit(42)
    } finally {
        System.exit(0)
    }
}
