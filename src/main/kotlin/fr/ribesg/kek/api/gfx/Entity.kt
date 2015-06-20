package fr.ribesg.kek.api.gfx

/**
 * Represents an Entity which can be rendered and updated.
 *
 * @author Ribesg
 */
public interface Entity {

    /**
     * Renders this Entity.
     */
    public open fun render(): Unit = Unit

    /**
     * Updates this Entity.
     */
    public open fun update(delta: Float): Unit = Unit

    /**
     * Frees this Entity.
     */
    public open fun free(): Unit = Unit

}
