package com.github.puzzle.game.engine.stages;

import com.github.puzzle.core.loader.engine.stage.AbstractStage;
import com.github.puzzle.game.PuzzleRegistries;
import com.github.puzzle.game.events.OnLoadAssetsEvent;

public class RunLoadAssets extends AbstractStage {

    public RunLoadAssets() {
        super("Pre-loading assets");
    }

    @Override
    public void doStage() {
        OnLoadAssetsEvent event = new OnLoadAssetsEvent(this);
        PuzzleRegistries.EVENT_BUS.post(event);
    }

}
