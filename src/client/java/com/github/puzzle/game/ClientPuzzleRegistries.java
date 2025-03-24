package com.github.puzzle.game;

import com.github.puzzle.core.registries.GenericRegistry;
import com.github.puzzle.core.registries.IRegistry;
import finalforeach.cosmicreach.audio.GameSong;
import finalforeach.cosmicreach.util.Identifier;

import static com.github.puzzle.core.Constants.MOD_ID;

public class ClientPuzzleRegistries {

    public static final IRegistry<GameSong> SONGS = new GenericRegistry<>(Identifier.of(MOD_ID, "songs"));

}
