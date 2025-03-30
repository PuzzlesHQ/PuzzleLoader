package com.github.puzzle.game.mixins.common.fixes;

import com.github.puzzle.game.PuzzleRegistries;
import finalforeach.cosmicreach.sounds.GameSound;
import finalforeach.cosmicreach.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Mixin(GameSound.class)
public class GameSoundMixin {

    /**
     * @author Mr_Zombii
     * @reason addSoundRegistry
     */
    @Overwrite
    public static GameSound of(String id) {
        Identifier soundId = Identifier.of(id);
        if (PuzzleRegistries.SOUNDS.contains(soundId)) {
            return PuzzleRegistries.SOUNDS.get(soundId);
        } else {
            GameSound sound;
            try {
                Constructor<GameSound> c = GameSound.class.getDeclaredConstructor(Identifier.class);
                c.setAccessible(true);
                sound = c.newInstance(soundId);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            PuzzleRegistries.SOUNDS.store(soundId, sound);
            return sound;
        }
    }

}
