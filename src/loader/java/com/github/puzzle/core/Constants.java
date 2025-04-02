package com.github.puzzle.core;

import com.github.puzzle.core.loader.launch.Piece;
import com.github.puzzle.core.loader.launch.PuzzleClassLoader;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.core.loader.util.RawAssetLoader;
import com.github.puzzle.core.loader.util.Reflection;
import com.github.puzzle.core.loader.util.ResourceLocation;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

public class Constants {

    public static final String MOD_ID = "puzzle-loader";

    public static final EnvType SIDE = Piece.getSide();
    public static final String GAME_VERSION = getGameVersion();
    public static final String PUZZLE_VERSION = getPuzzleVersion();
    public static final IEventBus EVENT_BUS = new EventBus();
    public static final File PUZZLE_INFO_FOLDER = new File(".puzzle");
    public static final MixinEnvironment.CompatibilityLevel MIXIN_COMPAT_LEVEL = MixinEnvironment.CompatibilityLevel.JAVA_17;
    public static final boolean IS_PUZZLE_DEV_VERSION = getPuzzleVersion().equals("69.69.69");

    static {
        initEventBusLambda();
    }

    public static boolean shouldClose() {
        try {
            Class<?> gdxClass = Class.forName("com.badlogic.gdx");
            Object app = Reflection.getFieldContents(gdxClass, "app");
            if (app == null) return false;

            return Reflection.getFieldContents(app, "running");
        } catch (ClassNotFoundException ignored) {}

        return false;
    }

    private static void initEventBusLambda() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        alphabet += alphabet.toUpperCase();
        String[] abcs = alphabet.split("");
        for (String s : abcs) {
            EVENT_BUS.registerLambdaFactory(s, (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        }
    }

    private static String getPuzzleVersion() {
        RawAssetLoader.RawFileHandle handle = RawAssetLoader.getClassPathAsset(ResourceLocation.of("puzzle-loader:version.txt"));
        String version = handle.getString();
        handle.dispose();
        if (!version.contains(".")) {
            return "69.69.69";
        } else return version;
    }

    private static String getGameVersion() {
        return Piece.provider.getRawVersion();
    }
}
