package com.github.puzzle.game.mixins.common.fixes;

import com.github.puzzle.game.PuzzleRegistries;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.HashMap;

@Mixin(Item.class)
public interface ItemMixin {



    /**
     * @author Mr_Zombii
     * @reason Force registering items in the Item Registry
     *
     * @see PuzzleRegistries#ITEMS;
     */
    @Overwrite
    static void registerItem(Item item) {
        Logger logger = LogManager.getLogger("ItemRegistry");

        String strId = item.getID();

        if (strId == null) throw new RuntimeException("Item id cannot be null!");
        if (strId.isEmpty()) throw new RuntimeException("Item id cannot be empty!");
        if (!strId.contains(":")) strId = "base:" + strId;

        Identifier id = Identifier.of(strId);

        if (PuzzleRegistries.ITEMS.contains(id)) {
            logger.warn("Duplicate item for id: {}, could cause errors.", item.getID());
//            throw new DuplicateIDException("Duplicate item for id: " + item.getID());
            return;
        }

        PuzzleRegistries.ITEMS.store(id, item);
    }

    /**
     * @author Mr_Zombii
     * @reason Force item getter to use Item Registry
     *
     * @see PuzzleRegistries#ITEMS;
     */
    @Overwrite
    static Item getItem(String strId) {
        Identifier itemId = Identifier.of(strId);

        if (!PuzzleRegistries.ITEMS.contains(itemId) && strId.contains("[") && strId.endsWith("]")) {
            int paramIdx = strId.indexOf(91);
            String potentialBlockId = strId.substring(0, paramIdx);
            Block block = Block.getById(potentialBlockId);
            if (block != null) {
                BlockState defaultBlockState = block.getDefaultBlockState();
                String paramStr = strId.substring(paramIdx + 1, strId.length() - 1);
                if (paramStr.equals("default")) {
                    return defaultBlockState.getItem();
                }

                HashMap<String, Object> map = new HashMap<>();

                for(String paramKeyAndValue : paramStr.split(",")) {
                    if (paramKeyAndValue.contains("=")) {
                        String[] keyAndValue = paramKeyAndValue.split("=");
                        String key = keyAndValue[0];
                        String value = keyAndValue[1];
                        map.put(key, value);
                    }
                }

                BlockState curBlockState = defaultBlockState.getVariantWithParams(map);
                if (curBlockState != null) {
                    return curBlockState.getItem();
                }
            }
        }

        try {
            return PuzzleRegistries.ITEMS.get(Identifier.of(strId));
        } catch (Exception ignore) {
            return null;
        }
    }

}
