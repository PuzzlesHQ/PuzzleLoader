package com.github.puzzle.game.mixins.common.fixes;

import com.github.puzzle.core.localization.LanguageManager;
import com.github.puzzle.core.localization.TranslationKey;
import finalforeach.cosmicreach.lang.Lang;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Lang.class)
public class LangMixin {

    @Unique
    private static final TranslationKey TRANSLATION_KEY = new TranslationKey("puzzle-loader:none");

    /**
     * @author Mr_Zombii
     * @reason Add puzzle lang to vanilla stuff as fallback
     */
    @Inject(method = "getMappedString", at = @At(shift = At.Shift.BEFORE, opcode = Opcodes.LRETURN, ordinal = 3, value = "RETURN"), cancellable = true)
    public void getMappedString(String key, boolean checkFallbacks, CallbackInfoReturnable<String> cir) {
        if (!TRANSLATION_KEY.getIdentifier().equals(key))
            TRANSLATION_KEY.set(key);
        cir.setReturnValue(LanguageManager.string(TRANSLATION_KEY));
        return;
    }


}
