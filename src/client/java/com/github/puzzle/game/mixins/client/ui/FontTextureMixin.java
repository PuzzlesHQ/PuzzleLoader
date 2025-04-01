package com.github.puzzle.game.mixins.client.ui;

import com.badlogic.gdx.files.FileHandle;
import finalforeach.cosmicreach.CosmicReachFont;
import finalforeach.cosmicreach.FontTexture;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.puzzle.core.Constants.MOD_ID;

@Mixin(FontTexture.class)
public abstract class FontTextureMixin {

    @Unique
    private static FontTexture SGA_font;

    @Unique
    private static String SGA_fileName = "standard_galactic_alphabet.png";

    @Unique
    private static FontTexture createFontTexture(int unicodeStart, String fileName) {
        FileHandle fontFile = GameAssetLoader.loadAsset(Identifier.of(MOD_ID,"textures/font/" + fileName));
        if (fontFile == null) {
            return null;
        } else {
            FontTexture texture = new FontTexture(unicodeStart, fontFile);
            SGA_font = texture;
            CosmicReachFont.setAllFontsDirty();
            return texture;
        }
    }

    @Inject(method = "getFontTexOfChar", at = @At("HEAD"), cancellable = true)
    private static void getFontTexOfChar(char c, CallbackInfoReturnable<FontTexture> cir) {
        //TODO add a toggle for this
        if (true){
            int fontIndex = c / 256;
            if (SGA_font == null){
                int unicodeStart = fontIndex * 256;
                createFontTexture(unicodeStart, SGA_fileName);
            }
            cir.setReturnValue(SGA_font);
        }
    }

}
