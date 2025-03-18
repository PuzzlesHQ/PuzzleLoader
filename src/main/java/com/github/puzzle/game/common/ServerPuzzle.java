package com.github.puzzle.game.common;

import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.ModInitializer;
import com.github.puzzle.game.items.IModItem;
import com.github.puzzle.game.items.puzzle.BuilderWand;

public class ServerPuzzle implements ModInitializer {


    public ServerPuzzle() {
//        PuzzleRegistries.EVENT_BUS.subscribe(this);
    }

    @Override
    public void onInit() {
        IModItem.registerItem(new BuilderWand());
    }
}
