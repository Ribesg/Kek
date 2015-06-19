package fr.ribesg.kek.api

/**
 * Contains the engine's configuration.
 *
 * Values should be edited before calling [Game.run()].
 *
 * This isn't final and should be changed to some better system.
 *
 * @author Ribesg
 */
public object Config {

    /**
     * Window configuration.
     */
    public object Window {

        /**
         * Window's width.
         */
        public var WIDTH: Int = 1280

        /**
         * Window's height.
         */
        public var HEIGHT: Int = 768

        /**
         * Window's base title.
         */
        public var BASE_TITLE: String = "Kek"

    }

}
