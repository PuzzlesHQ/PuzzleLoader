package com.github.puzzle.game.mixins.client.music;

import com.badlogic.gdx.utils.Array;
import com.github.puzzle.game.ClientPuzzleRegistries;
import finalforeach.cosmicreach.audio.GameMusicManager;
import finalforeach.cosmicreach.audio.GameSong;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameMusicManager.class)
public class MusicManagerMixin {

    @Shadow public static Array<GameSong> gameSongs;

    @Redirect(method = "loadSong", at = @At(value = "INVOKE", target = "Lcom/badlogic/gdx/utils/Array;add(Ljava/lang/Object;)V"))
    private static <T> void loadSong(Array instance, T value) {
        gameSongs = null;
        ClientPuzzleRegistries.SONGS.store(((GameSong) value).id, (GameSong) value);
    }

}
