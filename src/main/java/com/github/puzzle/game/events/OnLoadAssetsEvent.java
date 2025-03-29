package com.github.puzzle.game.events;

import com.github.puzzle.core.loader.engine.GameLoader;

public class OnLoadAssetsEvent {

    public GameLoader.Stage stage;

    public OnLoadAssetsEvent(GameLoader.Stage stage) {
        this.stage = stage;
    }

    public void addTask(Runnable task) {
        stage.glTasks().add(task);
    }

}
