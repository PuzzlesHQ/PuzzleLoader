package com.github.puzzle.game.items.puzzle;

import com.badlogic.gdx.math.Vector3;
import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.game.items.IModItem;
import com.github.puzzle.game.items.data.DataTagManifest;
import com.github.puzzle.game.items.impl.AbstractItem;
import com.github.puzzle.game.keybindings.PuzzleControlSettings;
import com.github.puzzle.game.util.BlockSelectionUtil;
import com.github.puzzle.game.worldgen.schematics.Schematic;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.chat.Chat;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.items.ItemSlot;
import finalforeach.cosmicreach.util.Identifier;

import static com.github.puzzle.core.Constants.MOD_ID;

public class BuilderWand extends AbstractItem {

    WANDMODES wandmodes = WANDMODES.SELECTPOS;
    public static Vector3 pos1 = null;
    public static Vector3 pos2 = null;
    public static Schematic clipBoard;
    boolean nextPos = false;

    public BuilderWand() {
        super(Identifier.of(MOD_ID, "builder_wand"));
        manifest.addTag(IModItem.MODEL_ID_PRESET.createTag(IModItem.MODEL_2_5D_ITEM));
        manifest.addTag(IModItem.TEXTURE_LOCATION_PRESET.createTag(Identifier.of(MOD_ID, "baby_wand.png")));
        manifest.addTag(IModItem.IS_DEBUG_ATTRIBUTE.createTag(true));
    }

    @Override
    public void use(ItemSlot slot, Player player, BlockPosition targetPlaceBlockPos, BlockPosition targetBreakBlockPos, boolean isLeftClick) {
        if(isLeftClick) return;
        if(PuzzleControlSettings.keyCrouch.isPressed()){
            //GameSingletons.openBlockEntityScreen(player, player.getZone(GameSingletons.world), this);
            int size = WANDMODES.values().length;
            if(wandmodes.ordinal() == size - 1) wandmodes = WANDMODES.SELECTPOS;
            else wandmodes = WANDMODES.values()[wandmodes.ordinal()+1];
            if (Constants.SIDE == EnvType.CLIENT)
                Chat.MAIN_CLIENT_CHAT.addMessage(null, "Mode: "+ wandmodes.mode);
            return;
        }
        switch (wandmodes) {
            case SELECTPOS -> {
                setBlockPos(player);
            }
            case PASTE -> {
                pasteClipBoard(player);
            }
        }
    }

    private void pasteClipBoard(Player player) {
        if(clipBoard == null) {
            if (Constants.SIDE == EnvType.CLIENT)
                Chat.MAIN_CLIENT_CHAT.addMessage(null, "clipBoard is null, run /gs");
            return;
        }
        BlockPosition blockPosition = BlockSelectionUtil.getBlockPositionLookingAt();
        if(blockPosition == null) return;
        Schematic.genSchematicStructureAtGlobal(clipBoard, player.getZone(), player.getChunk(GameSingletons.world), blockPosition.getGlobalX(), blockPosition.getGlobalY(), blockPosition.getGlobalZ());
    }

    private void setBlockPos(Player player) {
        BlockPosition position = BlockSelectionUtil.getBlockPositionLookingAt();
        if(position == null) return;
        Vector3 vector3 = new Vector3(position.getGlobalX(), position.getGlobalY(), position.getGlobalZ());
        if(nextPos) {
            pos1 = vector3;
            nextPos = false;
            if (Constants.SIDE == EnvType.CLIENT)
                Chat.MAIN_CLIENT_CHAT.addMessage(null, "Pos1: "+ pos1);
        } else {
            pos2 = vector3;
            nextPos = true;
            if (Constants.SIDE == EnvType.CLIENT)
                Chat.MAIN_CLIENT_CHAT.addMessage(null, "Pos2:" + pos2);
        }
    }

    @Override
    public boolean isTool() {
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public String getName() {
        return "Schematic Wand";
    }

    public enum WANDMODES {
        SELECTPOS("select-positions"),
        PASTE("paste");

        private final String mode;

        WANDMODES(String versionName){
            this.mode = versionName;
        }

        public static WANDMODES getMode(String str){
            return switch (str) {
                case "Select Positions" -> SELECTPOS;
                case "Paste" -> PASTE;
                default -> SELECTPOS;
            };
        }
    }
}
