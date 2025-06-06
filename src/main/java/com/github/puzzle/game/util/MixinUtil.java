package com.github.puzzle.game.util;

import com.github.puzzle.core.loader.util.MethodUtil;
import com.github.puzzle.core.loader.util.Reflection;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.launch.platform.CommandLineOptions;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.List;

@ApiStatus.Internal
public class MixinUtil {

    final static String MIXIN_START = "start";
    final static String MIXIN_DO_INIT = "doInit";
    final static String MIXIN_INJECT = "inject";
    final static String MIXIN_GOTO_PHASE = "gotoPhase";

    public static void start() {
        MethodUtil.runStaticMethod(Reflection.getMethod(MixinBootstrap.class, MIXIN_START));
    }

    public static void goToPhase(MixinEnvironment.Phase phase) {
        MethodUtil.runStaticMethod(Reflection.getMethod(MixinEnvironment.class, MIXIN_GOTO_PHASE, MixinEnvironment.Phase.class), phase);
    }

    public static void doInit(String[] args) {
        MethodUtil.runStaticMethod(Reflection.getMethod(MixinBootstrap.class, MIXIN_DO_INIT, CommandLineOptions.class), CommandLineOptions.of(List.of(args)));
    }

    public static void doInit(List<String> args) {
        MethodUtil.runStaticMethod(Reflection.getMethod(MixinBootstrap.class, MIXIN_DO_INIT, CommandLineOptions.class), CommandLineOptions.of(args));
    }

    public static void doInit(CommandLineOptions args) {
        MethodUtil.runStaticMethod(Reflection.getMethod(MixinBootstrap.class, MIXIN_DO_INIT, CommandLineOptions.class), args);
    }

    public static void inject() {
        MethodUtil.runStaticMethod(Reflection.getMethod(MixinBootstrap.class, MIXIN_INJECT));
    }

}
