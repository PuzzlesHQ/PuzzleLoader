package com.github.puzzle.game.common;

import com.github.puzzle.core.loader.engine.events.RegisterStagesEvent;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.ModInitializer;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.PostModInitializer;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.PreModInitializer;
import com.github.puzzle.game.PuzzleRegistries;
import com.github.puzzle.game.aprilfoolsupdate.ForeshadowingBlockEntity;
import com.github.puzzle.game.engine.stages.RunFinishLoading;
import com.github.puzzle.game.engine.stages.RunLoadAssets;
import com.github.puzzle.game.engine.stages.RunModInitialize;
import com.github.puzzle.game.engine.stages.RunModPostInitialize;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.io.SaveLocation;
import meteordevelopment.orbit.EventHandler;

import java.io.File;
import java.nio.file.Paths;

public class ServerPuzzle implements PreModInitializer, PostModInitializer, ModInitializer {

    public static int instanceCount = 0;

    public ServerPuzzle() {
        PuzzleRegistries.EVENT_BUS.subscribe(ServerPuzzle.class);

        RuntimeInfo.gameVariant = "Puzzled";
    }

    @EventHandler
    public static void onEvent(RegisterStagesEvent event) {
        event.register(new RunModInitialize());
        event.register(new RunLoadAssets());
        event.register(new RunFinishLoading());
        event.register(new RunModPostInitialize());
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onPostInit() {
        Block.loadBlock(PuzzleGameAssetLoader.locateAsset("base:blocks/foreshadowing.json"))
        .getDefaultBlockState().catalogHidden = false;
        Block.loadBlock(PuzzleGameAssetLoader.locateAsset("base:blocks/red_stone.json"))
        .getDefaultBlockState().catalogHidden = false;

        ForeshadowingBlockEntity.register();
    }

    @Override
    public void onPreInit() {
        File saveLocation = new File(SaveLocation.getSaveFolderLocation()).getAbsoluteFile();
        File puzzleFolder = new File(saveLocation, ".puzzle");
    }
}
