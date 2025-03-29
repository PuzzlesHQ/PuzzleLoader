package com.github.puzzle.game.mixins.server.entrypoint;

import com.github.puzzle.core.loader.engine.GameLoader;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerSingletons.class)
public class GameSingletonsMixin {

    /**
     * @author Mr_Zombii
     * @reason Make the game boot.
     */
    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/GameSingletons;postCreate()V"))
    private static void postCreate() {
        GameLoader loader = new GameLoader();
        loader.bar1 = loader.bar2 = loader.bar3 = GameLoader.ProgressBar.NULL_BAR;
        loader.create();

        while (!loader.finished.get()) {
            loader.update();
        }
    }

}