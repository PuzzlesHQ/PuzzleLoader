package com.github.puzzle.game;

import com.github.puzzle.core.Constants;
import com.github.puzzle.core.localization.LanguageRegistry;
import com.github.puzzle.core.registries.GenericRegistry;
import com.github.puzzle.core.registries.IRegistry;
import com.github.puzzle.game.entity.EntityRegistry;
import com.github.puzzle.game.loot.PuzzleLootTable;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.sounds.GameSound;
import finalforeach.cosmicreach.util.Identifier;
import meteordevelopment.orbit.IEventBus;

import static com.github.puzzle.core.Constants.MOD_ID;

/**
 * List of all available registries
 * as public static final fields
 */
public class PuzzleRegistries {

    public static final IEventBus EVENT_BUS = Constants.EVENT_BUS;
    public static final IRegistry<PuzzleLootTable> LOOT_TABLES = new GenericRegistry<>(Identifier.of(MOD_ID, "loot_tables"));
    public static final LanguageRegistry LANGUAGES = new LanguageRegistry(Identifier.of(MOD_ID, "languages"));

    public static final IRegistry<Item> ITEMS = new GenericRegistry<>(Identifier.of(MOD_ID, "items"));
    public static final EntityRegistry ENTITY_TYPE = new EntityRegistry(Identifier.of(MOD_ID, "entities"));
    public static final IRegistry<GameSound> SOUNDS = new GenericRegistry<>(Identifier.of(MOD_ID, "sounds"));

}
