package fr.ribesg.kek.api.gfx

import java.util.LinkedList

/**
 * Represents a Layer of [Entities][Entity] which can be updated and rendered.
 *
 * @author Ribesg
 */
public open class Layer : Entity {

    /**
     * Entities on this Layer.
     */
    protected val entities: MutableList<Entity> = LinkedList()

    /**
     * Renders this Layer.
     *
     * The default implementation simply renders all the entities.
     */
    override fun render(): Unit =
        entities.forEach(Entity::render)

    /**
     * Updates this Layer.
     *
     * The default implementation simply updates all the entities.
     */
    override fun update(delta: Float): Unit =
        entities.forEach { it.update(delta) }

}
