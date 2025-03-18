package com.github.puzzle.game.items.impl;

import com.github.puzzle.game.items.IModItem;
import com.github.puzzle.game.items.data.DataTagManifest;
import finalforeach.cosmicreach.util.Identifier;

@Deprecated(forRemoval = true, since = "2.3.9")
public class BasicItem extends AbstractItem {

    Identifier toolResource;

    public BasicItem(Identifier id) {
        super(id);
        manifest.addTag(IModItem.MODEL_ID_PRESET.createTag(IModItem.MODEL_2_5D_ITEM));
        manifest.addTag(IModItem.TEXTURE_LOCATION_PRESET.createTag(Identifier.of(id.getNamespace(), "textures/items/" + id.getName() + ".png")));
    }

    public BasicItem(Identifier id, Identifier location) {
        super(id);
        toolResource = location;
        manifest.addTag(IModItem.MODEL_ID_PRESET.createTag(IModItem.MODEL_2_5D_ITEM));
        manifest.addTag(IModItem.TEXTURE_LOCATION_PRESET.createTag(location));
    }

    public BasicItem(Identifier id, Identifier model_type_Id, Identifier location) {
        super(id);
        toolResource = location;
        manifest.addTag(IModItem.MODEL_ID_PRESET.createTag(model_type_Id));
        manifest.addTag(IModItem.TEXTURE_LOCATION_PRESET.createTag(location));
    }

}
