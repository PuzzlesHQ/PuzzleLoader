package com.github.puzzle.game.common;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientModInitializer;
import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientPostModInitializer;
import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientPreModInitializer;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.core.loader.provider.mod.AdapterPathPair;
import com.github.puzzle.core.loader.util.ModLocator;
import com.github.puzzle.core.loader.util.PuzzleEntrypointUtil;
import com.github.puzzle.core.localization.LanguageManager;
import com.github.puzzle.game.ClientGlobals;
import com.github.puzzle.game.PuzzleRegistries;
import com.github.puzzle.game.config.PuzzleConfig;
import com.github.puzzle.game.engine.stages.RunModInitialize;
import com.github.puzzle.game.engine.stages.RunModPostInitialize;
import com.github.puzzle.game.events.OnLoadAssetsEvent;
import com.github.puzzle.game.events.OnLoadAssetsFinishedEvent;
import com.github.puzzle.game.events.OnRegisterEvent;
import com.github.puzzle.game.events.OnRegisterLanguageEvent;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import com.github.puzzle.game.resources.VanillaAssetLocations;
import com.github.puzzle.game.ui.credits.CreditFile;
import com.github.puzzle.game.ui.credits.PuzzleCreditsMenu;
import com.github.puzzle.game.ui.credits.categories.ICreditElement;
import com.github.puzzle.game.ui.credits.categories.ImageCredit;
import com.github.puzzle.game.ui.credits.categories.ListCredit;
import com.github.puzzle.game.ui.modmenu.ConfigScreenFactory;
import com.github.puzzle.game.ui.modmenu.ModMenu;
import com.google.common.collect.ImmutableCollection;
import de.pottgames.tuningfork.SoundBuffer;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.Threads;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.sounds.GameSound;
import finalforeach.cosmicreach.util.Identifier;
import meteordevelopment.orbit.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle implements ClientPreModInitializer, ClientModInitializer, ClientPostModInitializer {

    public Puzzle() {
        PuzzleRegistries.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public void onEvent(OnRegisterLanguageEvent event) {
        LanguageManager.selectLanguage(event.registerLanguage(ClientGlobals.LanguageEnUs).locale());
    }

    @EventHandler
    public void onEvent(OnRegisterEvent event) {
        if (event.obj instanceof Item) {

        }
        if (event.obj instanceof GameSound) {

        }
    }

    @EventHandler
    public void onEvent(OnLoadAssetsEvent event) {
        List<Identifier> textures = new ArrayList<>(VanillaAssetLocations.getInternalFiles("textures", ".png"));
        textures.addAll(VanillaAssetLocations.getInternalFiles("lang/textures", ".png"));

        textures.forEach( location -> {
            PuzzleGameAssetLoader.LOADER.loadResource(location, Texture.class);
        });

        List<Identifier> sounds = new ArrayList<>(VanillaAssetLocations.getInternalFiles("sounds/", ".ogg"));

        for (String space : GameAssetLoader.getAllNamespaces()) {
            sounds.addAll(VanillaAssetLocations.getVanillaModFiles(space, "sounds/", ".ogg"));
        }

        sounds.forEach( location -> {
            PuzzleGameAssetLoader.LOADER.loadResource(location, SoundBuffer.class);
        });

        AssetManager manager = PuzzleGameAssetLoader.LOADER.getAssetManager();
        event.addTask(() -> event.stage.getGameLoader().bar2.setMax(manager.getQueuedAssets()));
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < manager.getQueuedAssets(); i++) {
            event.addTask(() -> event.stage.getGameLoader().bar2.setProgress(count.incrementAndGet()));
            event.addTask(manager::update);
        }
        event.addTask(manager::finishLoading);
        event.addTask(() -> Constants.EVENT_BUS.post(new OnLoadAssetsFinishedEvent()));
    }

    @Override
    public void onInit() {
        ICreditElement.TYPE_TO_ELEMENT.put("image", ImageCredit.class);
        ICreditElement.TYPE_TO_ELEMENT.put("list", ListCredit.class);

        PuzzleEntrypointUtil.invoke("modmenu", ConfigScreenFactory.class, (configScreen) -> {
            ModLocator.locatedMods.values().forEach(modContainer -> {
                ImmutableCollection<AdapterPathPair> collection = modContainer.INFO.Entrypoints.getOrDefault("modmenu", null);
                if (collection != null) {
                    collection.forEach(adapterPathPair -> {
                        if(adapterPathPair.getValue().equals(configScreen.getClass().getName())) {
                            ModMenu.registerModConfigScreen(modContainer.ID, configScreen);
                        }
                    });
                }
            });
        });

        Commands.register();

    }

    @Override
    public void onPostInit() {
//        Threads.runOnMainThread(() -> {
//            ClientPuzzleRegistries.BLOCK_MODEL_GENERATOR_FUNCTIONS.store(Identifier.of(Constants.MOD_ID, "base_block_model_generator"), (blockId) -> {
//                BlockModelGenerator generator = new BlockModelGenerator(blockId, "model");
//                generator.createTexture("all", Identifier.of("puzzle-loader", "textures/blocks/example_block.png"));
//                generator.createCuboid(0, 0, 0, 16, 16, 16, "all");
//                return List.of(generator);
//            });
//        });

//        BuiltInTags.ore.add(Block.getInstance("base:ore_gold"));
//        BuiltInTags.ore.add(Block.getInstance("base:ore_bauxite"));
//        BuiltInTags.ore.add(Block.getInstance("base:ore_iron"));
//
//        BuiltInTags.stone.add(Block.getInstance("base:stone_basalt"));
//        BuiltInTags.stone.add(Block.getInstance("base:stone_gabbro"));
//        BuiltInTags.stone.add(Block.getInstance("base:stone_limestone"));
//
//        BuiltInTags.glass.add(Block.getInstance("base:glass"));
//
//        BuiltInTags.grass.add(Block.getInstance("base:grass"));
//        BuiltInTags.dirt.add(Block.getInstance("base:dirt"));
//        BuiltInTags.dirt.add(Block.getInstance("base:lunar_soil"));
//        BuiltInTags.dirt.add(Block.getInstance("base:lunar_soil_packed"));
//
//        BuiltInTags.aluminum_block.add(Block.getInstance("base:aluminium_panel"));
//        BuiltInTags.aluminum_ore.add(Block.getInstance("base:ore_bauxite"));
//        BuiltInTags.aluminum_ingot.add(Item.getItem("base:ingot_aluminium"));
//
//        BuiltInTags.iron_ore.add(Block.getInstance("base:metal_panel"));
//        BuiltInTags.iron_block.add(Block.getInstance("base:ore_iron"));
//        BuiltInTags.iron_ingot.add(Item.getItem("base:ingot_iron"));
//
//        BuiltInTags.gold_ore.add(Block.getInstance("base:grass"));
//        BuiltInTags.gold_block.add(Block.getInstance("base:metal_panel"));
//        BuiltInTags.gold_ingot.add(Item.getItem("base:ingot_gold"));
//
//        BuiltInTags.logs.add(Block.getInstance("base:tree_log"));
//        BuiltInTags.planks.add(Block.getInstance("base:wood_planks"));
//        BuiltInTags.light.add(Block.getInstance("base:light"));

        Threads.runOnMainThread(() -> {
            PuzzleCreditsMenu.addFile(CreditFile.fromJson(PuzzleGameAssetLoader.locateAsset("puzzle-loader:credits/credits.json").readString()));
        });
    }

    @Override
    public void onPreInit() {
        RunModInitialize.initializers.add(new RunModInitialize.Initializer<>(
                EnvType.CLIENT,
                ClientModInitializer.ENTRYPOINT_KEY,
                ClientModInitializer.class,
                ClientModInitializer::onInit
        ));

        RunModPostInitialize.initializers.add(new RunModInitialize.Initializer<>(
                EnvType.CLIENT,
                ClientPostModInitializer.ENTRYPOINT_KEY,
                ClientPostModInitializer.class,
                ClientPostModInitializer::onPostInit
        ));

        PuzzleConfig.loadPuzzleConfig();
    }
}
