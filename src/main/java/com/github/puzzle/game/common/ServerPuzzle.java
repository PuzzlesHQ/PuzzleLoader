package com.github.puzzle.game.common;

import com.github.puzzle.core.loader.engine.events.RegisterStagesEvent;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.ModInitializer;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.PreModInitializer;
import com.github.puzzle.game.PuzzleRegistries;
import com.github.puzzle.game.engine.stages.RunFinishLoading;
import com.github.puzzle.game.engine.stages.RunLoadAssets;
import com.github.puzzle.game.engine.stages.RunModInitialize;
import com.github.puzzle.game.engine.stages.RunModPostInitialize;
import meteordevelopment.orbit.EventHandler;

public class ServerPuzzle implements PreModInitializer, ModInitializer {

    public static int instanceCount = 0;

    public ServerPuzzle() {
        PuzzleRegistries.EVENT_BUS.subscribe(ServerPuzzle.class);

        System.out.println("Number of Instances Made: " + (++instanceCount));
    }

    @EventHandler
    public static void onEvent(RegisterStagesEvent event) {
        event.register(new RunModInitialize());
        event.register(new RunLoadAssets());
        event.register(new RunFinishLoading());
        event.register(new RunModPostInitialize());
    }

    @Override
    public void onInit() {}

    @Override
    public void onPreInit() {
    }
}
