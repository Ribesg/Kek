package fr.ribesg.kek.api

import fr.ribesg.kek.api.gfx.Layer
import fr.ribesg.kek.impl.Core

/**
 * Game using the engine should implement this interface.
 *
 * The Game class acts as the main [Layer] which will be
 * updated and rendered by the engine.
 *
 * @author Ribesg
 */
public abstract class Game : Layer() {

    /**
     * Configures this Game.
     *
     * This is the only place where values in [Config] can be modified.
     */
    public open fun configure(): Unit = Unit

    /**
     * Initializes this Game.
     *
     * Here we can populate our entities and do any initialization.
     */
    public open fun init(): Unit = Unit

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
