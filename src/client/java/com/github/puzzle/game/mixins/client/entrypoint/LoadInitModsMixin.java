package com.github.puzzle.game.mixins.client.entrypoint;

import finalforeach.cosmicreach.BlockGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGame.class)
public class LoadInitModsMixin {
    @Shadow public static boolean gameStarted;

    @Inject(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ClientSingletons;create()V", shift = At.Shift.AFTER), cancellable = true)
    public void onInit(CallbackInfo ci) {
        ci.cancel();
        gameStarted = true;
    }
}
