package com.github.puzzle.game.mixins.client.model;

import com.github.puzzle.game.engine.blocks.BlockModelJsonInitializer;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJson;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJsonCuboidFace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockModelJson.class)
public abstract class BlockModelJsonInitDisabler implements BlockModelJsonInitializer.BLOCK_MODEL_JSON_INITIALIZER {

    @Shadow protected abstract void initialize(int rotX, int rotY, int rotZ);

    @Unique
    int puzzleLoader$rotX, puzzleLoader$rotY, puzzleLoader$rotZ;
    @Unique
    boolean puzzleLoader$canInit;

    @Inject(method = "initialize", cancellable = true, at = @At("HEAD"))
    public void init0(int rotX, int rotY, int rotZ, CallbackInfo ci) {
        this.puzzleLoader$rotX = rotX;
        this.puzzleLoader$rotY = rotY;
        this.puzzleLoader$rotZ = rotZ;

        if (!puzzleLoader$canInit) {
            ci.cancel();
            return;
        }
    }

    @Override
    public BlockModelJson init() {
        puzzleLoader$canInit = true;
        initialize(puzzleLoader$rotX, puzzleLoader$rotY, puzzleLoader$rotZ);
        return (BlockModelJson) (Object) this;
    }
}
