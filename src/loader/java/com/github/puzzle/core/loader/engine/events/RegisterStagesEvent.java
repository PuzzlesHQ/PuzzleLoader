package com.github.puzzle.core.loader.engine.events;

import com.github.puzzle.core.loader.engine.GameLoader;

/**
 * An event that is called before the game-loader starts to collect game-loading stages.
 *
 * @author Mr_Zombii
 * @since 3.0.0
 *
 * @see com.github.puzzle.core.loader.engine.GameLoader
 */
public record RegisterStagesEvent(GameLoader loader) {

    /**
     * A register method that adds the stage to the game-loader before execution,
     * all events under the package <code>com.github.puzzle.game.engine.stages</code> are prioritized and ran first.
     *
     * @author Mr_Zombii
     * @since 3.0.0
     *
     * @see com.github.puzzle.core.loader.engine.GameLoader.Stage
     * @see com.github.puzzle.core.loader.engine.stage.AbstractStage
     */
    public void register(GameLoader.Stage stage) {
        loader.register(stage);
    }

}
