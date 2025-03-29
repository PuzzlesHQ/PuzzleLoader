package com.github.puzzle.game.engine.stages;

import com.github.puzzle.core.loader.engine.stage.AbstractStage;
import finalforeach.cosmicreach.Threads;
import finalforeach.cosmicreach.blockentities.BlockEntityCreator;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.items.ItemThing;
import finalforeach.cosmicreach.items.loot.Loot;
import finalforeach.cosmicreach.items.recipes.CraftingRecipes;

public class RunFinishLoading extends AbstractStage {

    public RunFinishLoading() {
        super("Finish loading Cosmic Reach");
    }

    @Override
    public void doStage() {
        Threads.runOnMainThread(() -> {
            BlockEvents.initBlockEvents();
            Block.loadAllBlocks();
            ItemThing.loadAll();
            BlockEntityCreator.registerBlockEntityCreators();
            Loot.loadLoot();
            CraftingRecipes.loadCraftingRecipes();
        });
    }
}
