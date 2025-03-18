package com.github.puzzle.game.engine.blocks;

import com.badlogic.gdx.utils.Json;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import com.github.puzzle.game.resources.VanillaAssetLocations;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;
import finalforeach.cosmicreach.rendering.blockmodels.DummyBlockModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DummyBlockModelFactory implements IBlockModelFactory {


    public record InstanceKey(String modelName, float x, float y, float z) {}

    public final Map<InstanceKey, BlockModel> models = new LinkedHashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger("Puzzle | BlockModelFactory");

    private static String getNotShitModelName(String modelName){
        if(modelName.startsWith("gen_model::")) {
            modelName = modelName.substring("gen_model::".length()) + "_gen_model";
        }
        return modelName;
    }

    public void registerBlockModel(String modelName, float[] rotation, BlockModel model) {
        modelName = getNotShitModelName(modelName);
        final InstanceKey key = new InstanceKey(modelName, rotation[0], rotation[1], rotation[2]);
        if (models.containsKey(key)) {
            return;
        }
        models.put(key, model);
    }

    public BlockModel createFromJson(String modelName, float[] rotation, String modelJson) {
        modelName = getNotShitModelName(modelName);
        final InstanceKey key = new InstanceKey(modelName, rotation[0], rotation[1], rotation[2]);
        if (models.containsKey(key)) {
            return models.get(key);
        }

        DummyBlockModel model = DummyBlockModel.getInstanceFromJsonStr(modelName, modelJson, rotation);
        String parent = getModelParent(model);
        if (parent != null) {
            getInstance(parent, rotation);
        }

        models.put(key, model);
        return model;
    }

    @Override
    public BlockModel getInstance(String modelName, float[] rotation) {
        modelName = getNotShitModelName(modelName);
        final InstanceKey key = new InstanceKey(modelName, rotation[0], rotation[1], rotation[2]);
        if (models.containsKey(key)) {
            return models.get(key);
        }

        String modelJson = PuzzleGameAssetLoader.locateAsset(VanillaAssetLocations.getBlockModel(modelName)).readString();
        DummyBlockModel model = DummyBlockModel.getInstanceFromJsonStr(modelName, modelJson, rotation);

        String parent = getModelParent(model);
        if (parent != null) {
            getInstance(parent, rotation);
        }

        models.put(key, model);
        return model;
    }

    public static String getModelParent(DummyBlockModel model) {
//        try {
//            Field f = DummyBlockModel.class.getDeclaredField("parent");
//            f.setAccessible(true);
//            return (String) f.get(model);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public void createGeneratedModelInstance(BlockState state, BlockModel parentModel, String parentModelName, String modelName, float[] rotation) {
        modelName = getNotShitModelName(modelName);
        final InstanceKey key = new InstanceKey(modelName, rotation[0], rotation[1], rotation[2]);
        if (models.containsKey(key)) {
            return;
        }

        Json json = new Json();
        json.setTypeName(null);

        String modelJson;
        modelJson = "{\"parent\": \"" + parentModelName + "\", \"textures\": {}" + "}";

        DummyBlockModel model = DummyBlockModel.getInstanceFromJsonStr(modelName, modelJson, rotation);
        String parent = getModelParent(model);
        if (parent != null) {
            model.cullsSelf = parentModel.cullsSelf;
            model.isTransparent = parentModel.isTransparent;
            getInstance(parent, rotation);
        }

        models.put(key, model);
    }

    private int getNumberOfParents(DummyBlockModel model) {
        int n = 0;
        String parent = getModelParent(model);
        while (parent != null) {
            DummyBlockModel parentModel = null;

            InstanceKey parentKey;
            for (int x = 0; x < 360; x += 90) {
                for (int y = 0; y < 360; y += 90) {
                    for (int z = 0; z < 360; z += 90) {
                        if (models.containsKey(parentKey = new InstanceKey(parent, x, y, z))) {
                            parent = parentModel == null ? null : getModelParent(parentModel);
                            n++;
                            return n;
                        }
                    }
                }
            }

            parent = null;
            n++;
        }
        return n;
    }

    public int compare(BlockModel o1, BlockModel o2) {
        if (o1 instanceof DummyBlockModel f1 && o2 instanceof DummyBlockModel f2) {
            return Integer.compare(getNumberOfParents(f1), getNumberOfParents(f2));
        }
        return 0;
    }

    public List<BlockModel> sort() {
        List<BlockModel> models = new ArrayList<>(this.models.values());
        models.sort(this::compare);
        return models;
    }

}