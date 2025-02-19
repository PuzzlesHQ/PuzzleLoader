package com.github.puzzle.game.mixins.server.fixes;

import com.github.puzzle.core.terminal.PLTerminalConsole;
import com.github.puzzle.game.ServerGlobals;
import finalforeach.cosmicreach.io.SaveLocation;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.server.ServerLauncher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

@Mixin(value = ServerLauncher.class)
public class ServerLocationFixer {

    @Inject(require = 0, method = "main", at = @At("HEAD"))
    private static void init(String[] args, CallbackInfo ci) {
        SaveLocation.saveLocationOverride = ServerGlobals.getServerLocation().getAbsolutePath();
    }

    @Redirect(require = 0, method = "main", at = @At(value = "INVOKE", target = "Ljava/net/URL;toURI()Ljava/net/URI;"))
    private static URI toURI(URL instance) {
        return ServerGlobals.getServerLocation().toURI();
    }

    @Redirect(require = 0, method = "main", at = @At(value = "INVOKE", target = "Ljava/io/File;getParentFile()Ljava/io/File;"))
    private static File getParentFile(File instance) {
        return instance;
    }

    @Redirect(require = 0, method = "main", at = @At(value = "INVOKE", target = "Ljava/io/File;getPath()Ljava/lang/String;"))
    private static String getPath(File instance) {
        return ServerGlobals.getServerLocation().getPath();
    }

    @Inject(require = 0, method = "main", at = @At(value = "FIELD", target = "Lfinalforeach/cosmicreach/networking/server/ServerSingletons;SERVER:Lfinalforeach/cosmicreach/networking/netty/NettyServer;", shift = At.Shift.AFTER))
    private static void consoleListener(String[] args, CallbackInfo ci) {
        ServerGlobals.isRunning = true;
        if(!ServerGlobals.isRunningOnParadox) {
            Thread thread = new Thread("Console Handler") {
                public void run() {
                    try {
                        System.in.available();
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                    new PLTerminalConsole(ServerSingletons.SERVER).start();

                }

            };
            thread.setDaemon(true);
            thread.start();
        }
    }

    @Inject(require = 0, method = "main", at = @At("TAIL"))
    private static void setIsRunning(String[] args, CallbackInfo ci) {
        ServerGlobals.isRunning = false;
    }
}
