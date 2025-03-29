package com.github.puzzle.game.mixins.client.logging;

import com.badlogic.gdx.assets.AssetManager;
import com.github.puzzle.core.loader.engine.GameLoader;
import com.github.puzzle.core.loader.launch.Piece;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import finalforeach.cosmicreach.BlockGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGame.class)
public class BlockGameMixin {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("CosmicReach | BlockGame");

    @Redirect(method = "dispose", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/util/logging/Logger;info(Ljava/lang/Object;)V"), require = 0)
    private void print1(Object message) {
        LOGGER.info("{}", message);
    }

    @Redirect(method = "printGLInfo", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/util/logging/Logger;info(Ljava/lang/Object;)V"), require = 0)
    private static void print2(Object message) {
        LOGGER.info("{}", message);
    }

    @Inject(method = "dispose", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/audio/SoundManager;dispose()V", shift = At.Shift.BEFORE), require = 0)
    public void dispose(CallbackInfo ci) {
        GameLoader.killAll();

        AssetManager manager = PuzzleGameAssetLoader.LOADER.getAssetManager();
        manager.clear();
        manager.dispose();
        Piece.LOGGER.info("Puzzle API Destroyed");
    }
}