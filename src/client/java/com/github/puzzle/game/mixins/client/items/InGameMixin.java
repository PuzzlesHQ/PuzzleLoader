package com.github.puzzle.game.mixins.client.items;

import com.github.puzzle.game.items.IModItem;
import com.github.puzzle.game.networking.packet.items.UseModdedItemPacket;
import finalforeach.cosmicreach.BlockSelection;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.items.ItemStack;
import finalforeach.cosmicreach.networking.client.ClientNetworkManager;
import finalforeach.cosmicreach.settings.ControlSettings;
import finalforeach.cosmicreach.ui.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class InGameMixin {

    @Shadow private static Player localPlayer;
    @Shadow public BlockSelection blockSelection;
    boolean isPressed;

    @Inject(method = "update", at = @At("HEAD"))
    private void update(float deltaTime, CallbackInfo ci) {
            if (UI.hotbar.getSelectedSlot() != null){
                ItemStack stack = UI.hotbar.getSelectedSlot().getItemStack();
                if (stack != null && stack.getItem() instanceof IModItem modItem) {
                    if ((ControlSettings.keyUsePlace.isPressed() && !isPressed) || (ControlSettings.keyAttackBreak.isPressed() && !isPressed)) {
                        BlockPosition targetPlaceBlockPos = blockSelection.blockRaycasts.getPlacingBlockPos();
                        BlockPosition targetBreakBlockPos = blockSelection.blockRaycasts.getBreakingBlockPos();
                        boolean isLeftClick = ControlSettings.keyAttackBreak.isPressed();

                        if (!GameSingletons.isHost){
                            UseModdedItemPacket useModdedItemPacket = new UseModdedItemPacket(UI.hotbar.getSelectedSlotNum(), targetPlaceBlockPos, targetBreakBlockPos, isLeftClick);
                            ClientNetworkManager.sendAsClient(useModdedItemPacket);
                        }else {
                            modItem.use(UI.hotbar.getSelectedSlot(), localPlayer, targetPlaceBlockPos, targetBreakBlockPos, isLeftClick);
                        }
                        modItem.clientUse(UI.hotbar.getSelectedSlot(), localPlayer, targetPlaceBlockPos, targetBreakBlockPos, isLeftClick);
                        isPressed = true;
                    }
                    if ((isPressed && !ControlSettings.keyUsePlace.isPressed()) && (isPressed && !ControlSettings.keyAttackBreak.isPressed())) isPressed = false;
                }
            }
    }

}
