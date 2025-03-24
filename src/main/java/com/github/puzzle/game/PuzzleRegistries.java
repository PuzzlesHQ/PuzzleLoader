package com.github.puzzle.game;

import com.github.puzzle.core.localization.LanguageRegistry;
import com.github.puzzle.core.registries.GenericRegistry;
import com.github.puzzle.core.registries.IRegistry;
import com.github.puzzle.game.block.IModBlock;
import com.github.puzzle.game.block.PuzzleBlockAction;
import com.github.puzzle.game.entity.EntityRegistry;
import com.github.puzzle.game.factories.IFactory;
import com.github.puzzle.game.loot.PuzzleLootTable;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.sounds.GameSound;
import finalforeach.cosmicreach.util.Identifier;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import org.jetbrains.annotations.ApiStatus;

import java.lang.invoke.MethodHandles;

import static com.github.puzzle.core.Constants.MOD_ID;

/**
 * List of all available registries
 * as public static final fields
 */
public class PuzzleRegistries {

    public static final IEventBus EVENT_BUS = new EventBus();
    public static final IRegistry<PuzzleLootTable> LOOT_TABLES = new GenericRegistry<>(Identifier.of(MOD_ID, "loot_tables"));
    public static final LanguageRegistry LANGUAGES = new LanguageRegistry(Identifier.of(MOD_ID, "languages"));
    public static final IRegistry<IFactory<PuzzleBlockAction>> BLOCK_EVENT_ACTION_FACTORIES = new GenericRegistry<>(Identifier.of(MOD_ID, "block_event_actions_factories"));


    @ApiStatus.Internal
    public static final IRegistry<Runnable> BLOCK_MODEL_FINALIZERS = new GenericRegistry<>(Identifier.of(MOD_ID, "block_model_finalizers"));

    @ApiStatus.Internal
    public static final IRegistry<Runnable> BLOCK_FINALIZERS = new GenericRegistry<>(Identifier.of(MOD_ID, "block_finalizers"));

    static {
        EVENT_BUS.registerLambdaFactory("com.github.puzzle", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
    }

    public static final IRegistry<IModBlock> BLOCKS = new GenericRegistry<>(Identifier.of(MOD_ID, "blocks"));
    public static final IRegistry<Item> ITEMS = new GenericRegistry<>(Identifier.of(MOD_ID, "items"));
    public static final EntityRegistry ENTITY_TYPE = new EntityRegistry(Identifier.of(MOD_ID, "entities"));
    public static final IRegistry<GameSound> SOUND_REGISTRY = new GenericRegistry<>(Identifier.of(MOD_ID, "sounds"));

}
