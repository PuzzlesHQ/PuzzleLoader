package com.github.puzzle.game.mixins.client.entrypoint;

import com.github.puzzle.game.engine.GameBootingScreen;
import com.github.puzzle.game.ui.surface.Surface;
import com.github.puzzle.game.ui.surface.SurfaceUpdateRunner;
import finalforeach.cosmicreach.ClientSingletons;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientSingletons.class)
public class GameSingletonsMixin {

    /**
     * @author Mr_Zombii
     * @reason Make the game boot.
     */
    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/GameSingletons;postCreate()V"))
    private static void postCreate() {
        Surface.switchToSurface(new GameBootingScreen());

        SurfaceUpdateRunner.start();
    }

}
