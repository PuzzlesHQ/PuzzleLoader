package com.github.puzzle.game.common.excluded;

import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.launch.Piece;
import com.github.puzzle.core.loader.launch.PuzzleClassLoader;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.core.loader.meta.ModInfo;
import com.github.puzzle.core.loader.meta.Version;
import com.github.puzzle.core.loader.provider.IGameProvider;
import com.github.puzzle.core.loader.provider.mixin.PuzzleLoaderMixinService;
import com.github.puzzle.core.loader.provider.mod.ModContainer;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.CommonTransformerInitializer;
import com.github.puzzle.core.loader.util.ModLocator;
import com.github.puzzle.game.ServerGlobals;
import com.github.puzzle.game.resources.PuzzleGameAssetLoader;
import com.github.puzzle.game.util.MixinUtil;
import com.llamalad7.mixinextras.lib.apache.commons.tuple.Pair;
import io.netty.util.NettyRuntime;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleReference;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CosmicReachProvider implements IGameProvider {

    public CosmicReachProvider() {
        Piece.provider = this;
        System.setProperty("mixin.service", PuzzleLoaderMixinService.class.getName()); //Java 24 issues
        MixinUtil.start();
    }

    @Override
    public String getId() {
        return "cosmic-reach";
    }

    @Override
    public String getName() {
        return "Cosmic Reach";
    }

    @Override
    public Version getGameVersion() {
        return Version.parseVersion(getRawVersion());
    }

    String rawVersion;

    @Override
    public String getRawVersion() {
        if (rawVersion != null) return rawVersion;
        try {
            return rawVersion = new String(PuzzleGameAssetLoader.class.getResourceAsStream("/build_assets/version.txt").readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getEntrypoint() {
        if (Constants.SIDE == EnvType.SERVER) {
            return ServerGlobals.isRunningOnParadox ? ModLocator.PARADOX_SERVER_ENTRYPOINT : "finalforeach.cosmicreach.server.ServerLauncher";
        }

        return "finalforeach.cosmicreach.lwjgl3.Lwjgl3Launcher";
    }

    private URI getLocation(Module module) {
        if (module.isNamed() && module.getLayer() != null) {
            Configuration cf = module.getLayer().configuration();
            ModuleReference mref
                    = cf.findModule(module.getName()).get().reference();
            return mref.location().orElse(null);
        }
        return null;
    }

    public Collection<String> getArgs() {
        MixinUtil.goToPhase(MixinEnvironment.Phase.DEFAULT);
        return List.of(args);
    }

    @Override
    public void registerTransformers(PuzzleClassLoader classLoader) {
        ModLocator.getMods(EnvType.CLIENT, List.of(classLoader.getURLs()));

        CommonTransformerInitializer.invokeTransformers(classLoader);
    }

    String[] args;

    @Override
    public void initArgs(String[] args) {
        MixinUtil.doInit(args);
        this.args = args;
    }

    @Override
    public void inject(PuzzleClassLoader classLoader) {
        ModLocator.verifyDependencies();

        List<Pair<EnvType, String>> mixinConfigs = new ArrayList<>();
        for (ModContainer mod : ModLocator.locatedMods.values()) {
            if (!mod.INFO.MixinConfigs.isEmpty()) mixinConfigs.addAll(mod.INFO.MixinConfigs);
        }

        EnvType envType = Constants.SIDE;
        mixinConfigs.forEach((e) -> {
            if (envType == e.getKey() || e.getKey() == EnvType.UNKNOWN) {
                Mixins.addConfiguration(e.getRight());
            }
        });

        MixinUtil.inject();
    }

    @Override
    public void addBuiltinMods() {
        ModInfo.Builder cosmicModInfo = ModInfo.Builder.New();
        {
            cosmicModInfo.setName(getName());
            cosmicModInfo.setId("cosmic-reach");
            cosmicModInfo.setDesc("The base game.");
            cosmicModInfo.addAuthor("FinalForEach");
            cosmicModInfo.setVersion(getGameVersion());
            HashMap<String, JsonValue> meta = new HashMap<>();
            meta.put("icon", JsonObject.valueOf("icons/logox256.png"));
            cosmicModInfo.setMeta(meta);
            ModLocator.addMod(cosmicModInfo.build().getOrCreateModContainer());
        }

        ModInfo.Builder puzzleModInfo = ModInfo.Builder.New();
        {
            puzzleModInfo.setName("Puzzle Loader");
            puzzleModInfo.setId(Constants.MOD_ID);
            puzzleModInfo.setDesc("A new dedicated modloader for Cosmic Reach");
            puzzleModInfo.addEntrypoint("transformers", PuzzleTransformers.class.getName());
            puzzleModInfo.addDependency("cosmic-reach", getRawVersion());

            puzzleModInfo.addSidedMixinConfigs(
                    EnvType.UNKNOWN,
                    "mixins/common/logging.common.mixins.json",
                    "mixins/common/fixes.common.mixins.json",
                    "mixins/common/internal.common.mixins.json"
            );

            puzzleModInfo.addSidedMixinConfigs(
                    EnvType.CLIENT,
                    "mixins/client/accessors.client.mixins.json",
                    "mixins/client/internal.client.mixins.json",
                    "mixins/client/logging.client.mixins.json"
            );

            puzzleModInfo.addSidedMixinConfigs(
                    EnvType.SERVER,
                    "mixins/server/internal.server.mixins.json",
                    "mixins/server/fixes.server.mixins.json"
            );

            HashMap<String, JsonValue> meta = new HashMap<>();
            meta.put("icon", JsonObject.valueOf("puzzle-loader:icons/PuzzleLoaderIconx160.png"));
            puzzleModInfo.setMeta(meta);
            puzzleModInfo.setAuthors(new String[]{
                    "Mr-Zombii", "crabking", "repletsin5", "SinfullySoul", "tympanicblock61"
            });

            puzzleModInfo.setVersion(Constants.PUZZLE_VERSION);
            puzzleModInfo.addAccessManipulator("puzzle_loader.manipulator");
            puzzleModInfo.addEntrypoint("init", "com.github.puzzle.game.common.ServerPuzzle");
            puzzleModInfo.addEntrypoint("preInit","com.github.puzzle.game.common.ServerPuzzle");
            puzzleModInfo.addEntrypoint("postInit", "com.github.puzzle.game.common.ServerPuzzle");

            puzzleModInfo.addEntrypoint("client_init", "com.github.puzzle.game.common.Puzzle");
            puzzleModInfo.addEntrypoint("client_preInit","com.github.puzzle.game.common.Puzzle");
            puzzleModInfo.addEntrypoint("client_postInit", "com.github.puzzle.game.common.Puzzle");

            ModLocator.addMod(puzzleModInfo.build().getOrCreateModContainer());
        }
        ModLocator.addMod(puzzleModInfo.build().getOrCreateModContainer());
        ModLocator.addMod(cosmicModInfo.build().getOrCreateModContainer());
    }

    @Override
    public String getDefaultNamespace() {
        return "base";
    }

}
