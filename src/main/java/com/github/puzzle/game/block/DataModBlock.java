package com.github.puzzle.game.block;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.OrderedMap;
import com.github.puzzle.game.block.generators.BlockGenerator;
import com.github.puzzle.game.block.generators.PassThroughBlockGenerator;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import finalforeach.cosmicreach.util.Identifier;

/**
 * @see IModBlock
 * This class allows loading regular Json files
 * as IModBlocks
 */
public class DataModBlock implements IModBlock {

    public static class JsonBlock implements Json.Serializable {
        public String stringId;
        public String blockEntityId;
        public OrderedMap<String, ?> blockEntityParams;
        public OrderedMap<String, String> defaultParams;
        public OrderedMap<String, BlockGenerator.State> blockStates;
        public JsonValue defaultProperties;

        @Override
        public void write(Json json) {
        }

        @Override
        public void read(Json json, JsonValue jsonData) {
            this.stringId = jsonData.getString("stringId");
            this.blockEntityId = jsonData.getString("blockEntityId", null);
            if (jsonData.has("defaultParams")) {
                json.readField(this, "defaultParams", jsonData);
            }

            if (jsonData.has("blockStates")) {
                json.readField(this, "blockStates", jsonData);
            }

            if (jsonData.has("blockEntityParams")) {
                json.readField(this, "blockEntityParams", jsonData);
            }
        }
    }

    public Identifier debugResourceLocation;
    public String blockJson;
    private Identifier identifier;

    public DataModBlock(Identifier json) {
        this(PuzzleGameAssetLoader.locateAsset(Identifier.of(json.getNamespace(), json.getName().startsWith("blocks/") ? json.getName() : "blocks/" + json.getName())).readString());
        this.debugResourceLocation = Identifier.of(json.getNamespace(), json.getName().startsWith("blocks/") ? json.getName() : "blocks/" + json.getName());
    }

    public DataModBlock(String blockJson) {
        this.blockJson = blockJson;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public BlockGenerator getBlockGenerator() {
        Json json = new Json();

        JsonBlock block = json.fromJson(JsonBlock.class, blockJson);
        identifier = Identifier.of(block.stringId);
//        BlockGenerator generator = new BlockGenerator(getIdentifier());
//        generator.blockEntityId = block.blockEntityId;
//        generator.blockEntityParams = block.blockEntityParams;
//        generator.defaultParams = block.defaultParams;
//        generator.blockStates = block.blockStates;
//        generator.defaultProperties = block.defaultProperties;
//        System.out.println(generator.generateJson());
//        return generator;
        return new PassThroughBlockGenerator(identifier, blockJson);
    }

}