package com.github.puzzle.core;

import com.github.puzzle.core.loader.launch.Piece;
import com.github.puzzle.core.loader.launch.PuzzleClassLoader;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.core.loader.util.Reflection;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

public class Constants {
    public static final String MOD_ID = "puzzle-loader";

    public static final EnvType SIDE = Piece.getSide();
    public static final IEventBus EVENT_BUS = new EventBus();

    public static boolean shouldClose() {

        try {
            Class<?> gdxClass = Class.forName("com.badlogic.gdx");
            Object app = Reflection.getFieldContents(gdxClass, "app");
            if (app == null) return false;

            return Reflection.getFieldContents(app, "running");
        } catch (ClassNotFoundException ignored) {}

        return false;
    }

    static {
        EVENT_BUS.registerLambdaFactory("com.github.puzzle", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
    }

    public static final MixinEnvironment.CompatibilityLevel MIXIN_COMPAT_LEVEL = MixinEnvironment.CompatibilityLevel.JAVA_17;

    public static InputStream getFile(String file) {
        InputStream input = Constants.class.getResourceAsStream(file);
        if (input == null) {
            input = PuzzleClassLoader.class.getClassLoader().getResourceAsStream(file);
        }
        return input;
    }

    public static final boolean isDev = getPuzzleVersion().equals("69.69.69");

    public static String getPuzzleVersion() {
            try {
                InputStream stream = getFile("/assets/puzzle-loader/version.txt");
                String bytez = new String(stream.readAllBytes()).strip();
                stream.close();
                if (!bytez.contains(".")) {
                    return "69.69.69";
                } else return bytez;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public static String getGameVersion() {
        try {
            InputStream stream = getFile("/build_assets/version.txt");
            String bytez = new String(stream.readAllBytes()).strip();
            stream.close();
            if (!bytez.contains(".")) {
                return "69.69.69";
            } else return bytez;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
