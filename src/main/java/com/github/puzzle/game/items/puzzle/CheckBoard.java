package com.github.puzzle.game.items.puzzle;

import com.github.puzzle.game.items.IModItem;
import com.github.puzzle.game.items.data.DataTagManifest;
import com.github.puzzle.game.items.impl.AbstractItem;
import finalforeach.cosmicreach.util.Identifier;

import static com.github.puzzle.core.Constants.MOD_ID;

public class CheckBoard extends AbstractItem {

    public CheckBoard() {
        super(Identifier.of(MOD_ID, "checker_board"));
        manifest.addTag(IModItem.IS_DEBUG_ATTRIBUTE.createTag(true));
        manifest.addTag(IModItem.MODEL_ID_PRESET.createTag(IModItem.MODEL_2_5D_ITEM));
        manifest.addTag(IModItem.TEXTURE_LOCATION_PRESET.createTag(Identifier.of(MOD_ID, "checker_board.png")));
    }

    @Override
    public String getName() {
        return "Debug | Checker Board";
    }

}
