package com.github.puzzle.game.mixins.common.logging;

import com.github.puzzle.core.loader.util.AnsiColours;
import finalforeach.cosmicreach.blocks.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockState.class)
public class BlockStateLogging {

    private static Logger LOGGER = LogManager.getLogger("Block");

    @Redirect(require = 0, method = "initialize(Lfinalforeach/cosmicreach/blocks/Block;)V", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/util/logging/Logger;info(Ljava/lang/Object;)V"))
    private void println(Object message) {
        String nString = ((String) message).replaceAll("Applying generator ", "");
        String[] strs = nString.split("for blockstate:");
        for (int i = 0; i < strs.length; i++) strs[i] = strs[i].strip();

        LOGGER.info("Transforming block state {}\"{}\"{} with generator {}\"{}\"", AnsiColours.BLUE, strs[1], AnsiColours.RESET, AnsiColours.BLUE, strs[0]);
    }

}
