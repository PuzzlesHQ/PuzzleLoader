package com.github.puzzle.game.engine.blocks;

import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;
import finalforeach.cosmicreach.rendering.blockmodels.IBlockModelInstantiator;

import java.util.List;

public interface IBlockModelFactory extends IBlockModelInstantiator {

    void createGeneratedModelInstance(BlockState state, BlockModel model, String parentModelName, String modelName, float[] rotation);
    BlockModel createFromJson(String modelName, float[] rotation, String modelJson);

    BlockModel getInstance(String modelName, float[] rotation);

    List<BlockModel> sort();
}
