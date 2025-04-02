package com.github.puzzle.game.common.excluded;

import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.annotation.Note;
import com.github.puzzle.core.loader.launch.Piece;
import com.github.puzzle.core.loader.launch.PuzzleClassLoader;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.core.loader.meta.ModInfo;
import com.github.puzzle.core.loader.meta.Version;
import com.github.puzzle.core.loader.provider.IGameProvider;
import com.github.puzzle.core.loader.provider.mod.ModContainer;
import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.CommonTransformerInitializer;
import com.github.puzzle.core.loader.util.ModLocator;
import com.github.puzzle.core.loader.util.RawAssetLoader;
import com.github.puzzle.game.ServerGlobals;
import com.github.puzzle.game.util.MixinUtil;
import com.llamalad7.mixinextras.lib.apache.commons.tuple.Pair;
import io.netty.util.NettyRuntime;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Note("You thought lol, you should see the look on your face.")
public class MinecraftProvider implements IGameProvider {

    public MinecraftProvider() {
        Piece.provider = this;

        MixinUtil.start();
    }

    @Override
    public String getId() {
        return "minecraft";
    }

    @Override
    public String getName() {
        return "Minecraft";
    }

    @Override
    public Version getGameVersion() {
        return Version.parseVersion(version);
    }

    Class<?> clazz;

    @Override
    public String getRawVersion() {
        return version;
    }

    @Override
    public String getEntrypoint() {
        String launcher = "/net/minecraft/server/Main.class";
        if (Constants.SIDE == EnvType.SERVER) {
            try {
                try {
                    PuzzleClassLoader.class.getClassLoader().getResourceAsStream(launcher);
                } catch (Exception ignore) {
                    launcher = "/net/minecraft/server/MinecraftServer.class";
                    PuzzleClassLoader.class.getClassLoader().getResourceAsStream(launcher);
                }
                return launcher.replaceAll("/", ".").replace(".class", "");
            } catch (Exception ignore) {
                throw new RuntimeException("Minecraft Server Main does not exist.");
            }
        }
        try {
            launcher = "/net/minecraft/client/main/Main.class";
            PuzzleClassLoader.class.getClassLoader().getResourceAsStream(launcher);
        } catch (Exception ignore) {
            throw new RuntimeException("Minecraft Client Main does not exist.");
        }
        return launcher.replaceAll("/", ".").replace(".class", "");
    }

    String version = "";

    @Override
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

        OptionParser optionparser = new OptionParser();
        optionparser.allowsUnrecognizedOptions();
        OptionSpec<String> versionSpec = optionparser.accepts("version").withRequiredArg().required();
        OptionSet optionSet = optionparser.parse(args);

        version = versionSpec.value(optionSet);
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
            cosmicModInfo.setId(getId());
            cosmicModInfo.setDesc("The base game.");
            cosmicModInfo.addAuthor("Mojang");
            cosmicModInfo.setVersion(getGameVersion());
            HashMap<String, JsonValue> meta = new HashMap<>();
            meta.put("icon", JsonObject.valueOf("pack.png"));
            cosmicModInfo.setMeta(meta);
            ModLocator.addMod(cosmicModInfo.build().getOrCreateModContainer());
        }
    }

    @Override
    public String getDefaultNamespace() {
        return "minecraft";
    }
}
