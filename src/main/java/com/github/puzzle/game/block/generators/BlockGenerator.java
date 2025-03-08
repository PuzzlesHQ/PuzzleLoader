package com.github.puzzle.game.block.generators;

import com.badlogic.gdx.utils.*;
import com.github.puzzle.game.engine.blocks.IBlockLoader;
import com.github.puzzle.game.factories.IGenerator;
import com.github.puzzle.game.oredict.tags.Tag;
import finalforeach.cosmicreach.GameTagList;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.placementrules.PlacementRules;
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
        public GameTagList tags;
        public String swapGroupId;
        public String dropId;
        public float hardness = 1.5F;
        public Predicate<BlockPosition> canPlaceCheck = GamePredicate.getAlwaysTrue();
        int rotXZ = 0;
        public OrderedMap<String, ?> dropParams;
        ObjectIntMap<String> intProperties = new ObjectIntMap();
        private PlacementRules placementRules = PlacementRules.DEFAULT_RULES;
        private boolean canDrop = true;

        public State() {}

        public void read(Json json, JsonValue jsonData) {
            if (jsonData.has("canPlace")) {
                JsonValue canPlaceRoot = jsonData.get("canPlace");
                this.canPlaceCheck = (Predicate)json.readValue(GamePredicateBlockPos.class, canPlaceRoot.child);
            }

            this.langKey = jsonData.getString("langKey", this.langKey);
            this.modelName = jsonData.getString("modelName", this.modelName);
            this.swapGroupId = jsonData.getString("swapGroupId", this.swapGroupId);
            this.blockEventsId = jsonData.getString("blockEventsId", this.blockEventsId);
            this.dropId = jsonData.getString("dropId", this.dropId);
            this.itemIcon = jsonData.getString("itemIcon", this.itemIcon);
            this.allowSwapping = jsonData.getBoolean("allowSwapping", this.allowSwapping);
            this.isOpaque = jsonData.getBoolean("isOpaque", this.isOpaque);
            this.canRaycastForBreak = jsonData.getBoolean("canRaycastForBreak", this.canRaycastForBreak);
            this.canRaycastForPlaceOn = jsonData.getBoolean("canRaycastForPlaceOn", this.canRaycastForPlaceOn);
            this.canDrop = jsonData.getBoolean("canDrop", this.canDrop);
            this.catalogHidden = jsonData.getBoolean("catalogHidden", this.catalogHidden);
            this.walkThrough = jsonData.getBoolean("walkThrough", this.walkThrough);
            this.canRaycastForReplace = jsonData.getBoolean("canRaycastForReplace", this.canRaycastForReplace);
            this.isFluid = jsonData.getBoolean("isFluid", this.isFluid);
            this.lightAttenuation = jsonData.getInt("lightAttenuation", this.lightAttenuation);
            this.lightLevelRed = jsonData.getInt("lightLevelRed", this.lightLevelRed);
            this.lightLevelGreen = jsonData.getInt("lightLevelGreen", this.lightLevelGreen);
            this.lightLevelBlue = jsonData.getInt("lightLevelBlue", this.lightLevelBlue);
            this.rotXZ = jsonData.getInt("rotXZ", this.rotXZ);
            this.hardness = jsonData.getFloat("hardness", this.hardness);
            this.blastResistance = jsonData.getFloat("blastResistance", this.blastResistance);
            this.friction = jsonData.getFloat("friction", this.friction);
            this.bounciness = jsonData.getFloat("bounciness", this.bounciness);
            this.refractiveIndex = jsonData.getFloat("refractiveIndex", this.refractiveIndex);
            if (jsonData.has("stateGenerators")) {
                json.readField(this, "stateGenerators", jsonData);
            }

            if (jsonData.has("tags")) {
                json.readField(this, "tags", jsonData);
            }

            if (jsonData.has("dropParams")) {
                json.readField(this, "dropParams", jsonData);
            }

            if (jsonData.has("intProperties")) {
                json.readField(this, "intProperties", jsonData);
            }

            if (jsonData.has("placementRules")) {
                this.placementRules = PlacementRules.get(jsonData.getString("placementRules"));
            }
        }

        public void write(Json json) {
            json.writeField(this, "langKey");
            json.writeField(this, "modelName");
            json.writeField(this, "swapGroupId");
            json.writeField(this, "blockEventsId");
            json.writeField(this, "dropId");
            json.writeField(this, "allowSwapping");
            json.writeField(this, "isOpaque");
            json.writeField(this, "canRaycastForBreak");
            json.writeField(this, "canRaycastForPlaceOn");
            json.writeField(this, "canRaycastForReplace");
            json.writeField(this, "canDrop");
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
            json.writeField(this, "dropParams");
            json.writeField(this, "intProperties");
            json.writeField(this, "tags");
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
