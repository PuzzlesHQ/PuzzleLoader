package com.github.puzzle.game.block.generators;

import com.badlogic.gdx.utils.*;
import com.github.puzzle.game.engine.blocks.IBlockLoader;
import com.github.puzzle.game.factories.IGenerator;
import com.github.puzzle.game.oredict.tags.Tag;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.predicates.GamePredicate;
import finalforeach.cosmicreach.util.predicates.GamePredicateBlockPos;
import org.hjson.JsonObject;
import org.hjson.Stringify;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


public class BlockGenerator implements IGenerator {

    public State defaultProperties;

    public static class State implements Json.Serializable {
        /**
         * modelName is used for locating the model used for this block
         * for blocks using models in vanilla json files the modelName is
         * a ResourceLocation
         * for blocks using custom models, the modelName is a combination
         * of blockId and modelName
         * this is handled by setting the usingBlockModelGenerator flag in
         * createBlockState
         */
        public String modelName;
        public String itemIcon;
        public int lightLevelRed = 0;
        public int lightLevelGreen = 0;
        public int lightLevelBlue = 0;
        public int lightAttenuation = 15;
        public String langKey;

        /**
         * better name would be eventName
         */
        public String blockEventsId = "base:block_events_default";
        public float blastResistance = 100.0F;
        public float friction = 1.0F;
        public float bounciness = 0.0F;
        public float refractiveIndex = 1.0F;
        @Deprecated
        public boolean generateSlabs = false;
        public String[] stateGenerators = null;
        public boolean catalogHidden = false;
        public boolean isOpaque = true;
        public boolean walkThrough = false;
        public boolean itemCatalogHidden = false;
        public boolean canRaycastForBreak = true;
        public boolean canRaycastForPlaceOn = true;
        public boolean canRaycastForReplace = false;
        public boolean isFluid = false;
        public boolean allowSwapping = false;
        public String[] tags;
        public String swapGroupId;
        public String dropId;
        public float hardness = 1.5F;
        public Predicate<BlockPosition> canPlaceCheck = GamePredicate.getAlwaysTrue();
        int rotXZ = 0;
        public OrderedMap<String, ?> dropParams;
        ObjectIntMap<String> intProperties = new ObjectIntMap();

        public State() {}

        public void read(Json json, JsonValue jsonData) {
            if (jsonData.has("canPlace")) {
                this.canPlaceCheck = json.readValue(GamePredicateBlockPos.class, jsonData.get("canPlace").child);
            }

            this.langKey = jsonData.getString("langKey", null);
            this.modelName = jsonData.getString("modelName", null);
            this.swapGroupId = jsonData.getString("swapGroupId", null);
            this.blockEventsId = jsonData.getString("blockEventsId", null);
            this.dropId = jsonData.getString("dropId", null);
            this.itemIcon = jsonData.getString("itemIcon", null);
            this.allowSwapping = jsonData.getBoolean("allowSwapping", true);
            this.isOpaque = jsonData.getBoolean("isOpaque", true);
            this.canRaycastForBreak = jsonData.getBoolean("canRaycastForBreak", true);
            this.canRaycastForPlaceOn = jsonData.getBoolean("canRaycastForPlaceOn", true);
            this.catalogHidden = jsonData.getBoolean("catalogHidden", false);
            this.walkThrough = jsonData.getBoolean("walkThrough", false);
            this.canRaycastForReplace = jsonData.getBoolean("canRaycastForReplace", false);
            this.isFluid = jsonData.getBoolean("isFluid", false);
            this.lightAttenuation = jsonData.getInt("lightAttenuation", 15);
            this.lightLevelRed = jsonData.getInt("lightLevelRed", 0);
            this.lightLevelGreen = jsonData.getInt("lightLevelGreen", 0);
            this.lightLevelBlue = jsonData.getInt("lightLevelBlue", 0);
            this.rotXZ = jsonData.getInt("rotXZ", 0);
            this.hardness = jsonData.getFloat("hardness", 1.5F);
            this.blastResistance = jsonData.getFloat("blastResistance", 100.0F);
            this.friction = jsonData.getFloat("friction", 1.0F);
            this.bounciness = jsonData.getFloat("bounciness", 0.0F);
            json.readField(this, "stateGenerators", jsonData);
            json.readField(this, "tags", jsonData);
            json.readField(this, "dropParams", jsonData);
            json.readField(this, "intProperties", jsonData);
            this.refractiveIndex = jsonData.getFloat("refractiveIndex", this.refractiveIndex);
        }

        public void write(Json json) {
            json.writeField(this, "langKey");
            json.writeField(this, "modelName");
            json.writeField(this, "itemIcon");
            json.writeField(this, "swapGroupId");
            json.writeField(this, "blockEventsId");
            json.writeField(this, "dropId");
            json.writeField(this, "allowSwapping");
            json.writeField(this, "isOpaque");
            json.writeField(this, "canRaycastForBreak");
            json.writeField(this, "canRaycastForPlaceOn");
            json.writeField(this, "canRaycastForReplace");
            json.writeField(this, "catalogHidden");
            json.writeField(this, "walkThrough");
            json.writeField(this, "isFluid");
            json.writeField(this, "lightAttenuation");
            json.writeField(this, "lightLevelRed");
            json.writeField(this, "lightLevelGreen");
            json.writeField(this, "lightLevelBlue");
            json.writeField(this, "rotXZ");
            json.writeField(this, "hardness");
            json.writeField(this, "friction");
            json.writeField(this, "bounciness");
            json.writeField(this, "refractiveIndex");
            json.writeField(this, "blastResistance");
            json.writeField(this, "stateGenerators");
            json.writeField(this, "tags");
            json.writeField(this, "dropParams");
            json.writeField(this, "intProperties");
            json.writeField(this, "canPlaceCheck");
        }

    }

    public Identifier blockId;
    public String blockEntityId;
    public Map<String, ?> blockEntityParams;

    public Map<String, String> defaultParams;
    public Map<String, State> blockStates;
    public List<Tag> itemTags;

    public BlockGenerator(Identifier blockId) {
        this.blockId = blockId;
        this.defaultParams = new LinkedHashMap<>();
        this.blockStates = new LinkedHashMap<>();
        this.blockEntityParams = new LinkedHashMap<>();
    }

    public void addBlockEntity(String blockEntityId, Map<String, ?> parameters) {
        this.blockEntityId = blockEntityId;
        this.blockEntityParams = parameters;
    }

    public State createBlockState(String id, String modelName, boolean usingBlockModelGenerator) {
        State state = new State();
        state.modelName = usingBlockModelGenerator ? blockId.toString() + "_" + modelName : modelName;
        state.blockEventsId = BlockEventGenerator.getEventName(blockId, "puzzle_default");
        blockStates.put(id, state);
        return state;
    }
    
    public State createBlockState(String id, String modelName, boolean usingBlockModelGenerator, String eventName, boolean usingBlockEventGenerator) {
        State state = new State();
        state.modelName = usingBlockModelGenerator ? blockId.toString() + "_" + modelName : modelName;
        state.blockEventsId = usingBlockEventGenerator ? BlockEventGenerator.getEventName(blockId, eventName) : eventName;
        blockStates.put(id, state);
        return state;
    }

    @Override
    public void register(IBlockLoader loader) {}

    public void addTags(Tag ...itemTag) {
        itemTags.addAll(List.of(itemTag));
    }

    @Override
    public String generateJson() {
        Json json = new Json();
        json.setTypeName(null);
        json.setOutputType(JsonWriter.OutputType.json);
        Json json2 = new Json();
        json2.setOutputType(JsonWriter.OutputType.json);
        String out = "{";
        out += "\"stringId\": \"" + blockId + "\", ";
        if (blockEntityId != null)
            out += "\"blockEntityId\": \"" + blockEntityId + "\", ";
        if (blockEntityParams != null)
            out += "\"blockEntityParams\": " + json2.toJson(blockEntityParams) + ", ";
        if (defaultProperties != null)
            out += "\"defaultProperties\": " + json2.toJson(defaultProperties) + ", ";
        out += "\"blockStates\": " + json.toJson(blockStates);
        out += "}";

        return JsonObject.readHjson(out).toString(Stringify.FORMATTED);
    }
}
