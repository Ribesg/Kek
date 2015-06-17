package fr.ribesg.kek

fun main(args: Array<String>) {
    System.setProperty("org.lwjgl.librarypath", "lwjgl3/lwjgl/native")
    Game().run()
}
