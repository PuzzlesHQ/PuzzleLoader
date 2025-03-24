package com.github.puzzle.game.mixins.client.music;

import com.badlogic.gdx.utils.JsonValue;
import com.github.puzzle.game.ClientPuzzleRegistries;
import com.llamalad7.mixinextras.sugar.Local;
import finalforeach.cosmicreach.audio.GameMusicManager;
import finalforeach.cosmicreach.audio.GameSong;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMusicManager.class)
public class MusicManagerMixin {

    @Inject(method = "loadSong", at = @At("TAIL"))
    private static void loadSong(JsonValue json, CallbackInfo ci, @Local GameSong song) {
        ClientPuzzleRegistries.SONG_REGISTRY.store(song.id, song);
    }

}
