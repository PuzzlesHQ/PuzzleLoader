package com.github.puzzle.game.mixins.client.ui;

import com.github.puzzle.game.ui.surface.Surface;
import finalforeach.cosmicreach.gamestates.GameState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameState.class)
public class GameStateMixin {

    @Shadow public static GameState currentGameState;

    /**
     * @author Mr_Zombii
     * @reason Get current & last gamestate.
     */
    @Inject(method = "lambda$switchToGameState$0", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/GameState;resize(II)V", shift = At.Shift.BEFORE))
    private static void switchToGameState(GameState gameState, CallbackInfo ci) {
        Surface.CURRENT_GAMESTATE.set(gameState);
        Surface.LAST_GAMESTATE.set(currentGameState);
    }
}
