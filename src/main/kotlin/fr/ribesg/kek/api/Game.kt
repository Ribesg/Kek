package fr.ribesg.kek.api

import fr.ribesg.kek.api.gfx.Entity
import fr.ribesg.kek.impl.Core
import java.util.LinkedList

/**
 * Game using the engine should implement this interface.
 *
 * @author Ribesg
 */
public abstract class Game {

    /**
     * Entities in this Game.
     */
    protected val entities: MutableList<Entity> = LinkedList()

    /**
     * Initializes this Game.
     *
     * Here we can populate our entities and do any initialization.
     */
    public open fun init(): Unit = Unit

    /**
     * Updates this Game.
     *
     * The default implementation simply updates all the entities.
     */
    public open fun update(delta: Float): Unit =
        entities.forEach { e -> e.update(delta) }

    /**
     * Renders this Game.
     *
     * The default implementation simply renders all the entities.
     */
    public open fun render(): Unit =
        entities.forEach(Entity::render)

    /**
     * Ends this Game.
     *
     * Here we should close resources and clean everything like threads.
     */
    public open fun end(): Unit = Unit

    // ------------------------------------------ //

    /**
     * Runs this Game.
     */
    public fun run() {
        Core.run(this)
    }

}
