package com.github.puzzle.game.mixins.client.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;
import finalforeach.cosmicreach.CosmicReachFont;
import finalforeach.cosmicreach.FontTexture;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.puzzle.core.Constants.MOD_ID;

@Mixin(FontTexture.class)
public abstract class FontTextureMixin {

    @Shadow public static IntMap<FontTexture> allFontTextures;
    @Shadow private static IntSet addedFontIndices;

    @Shadow
    static FontTexture createFontTexture(int unicodeStart, String fileName) {
        return null;
    }

    @Unique
    private static FontTexture SGA_font;

    @Unique
    private static String SGA_fileName = "standard_galactic_alphabet.png";

    @Unique
    private static FontTexture createFontTexturev2(int unicodeStart, String fileName) {
        int index = unicodeStart / 256;
        FileHandle fontFile = GameAssetLoader.loadAsset(Identifier.of(MOD_ID,"textures/font/" + fileName));
        addedFontIndices.add(index);
        if (fontFile == null) {
            return null;
        } else {
            FontTexture texture = new FontTexture(unicodeStart, fontFile);
            allFontTextures.put(index, texture);
            CosmicReachFont.setAllFontsDirty();
            return texture;
        }
    }

    /**
     * @author CrabKing
     * @reason Add fun thing >:)
     */
    @Overwrite
    public static FontTexture getFontTexOfChar(char c) {
        int fontIndex = c / 256;
        FontTexture t = allFontTextures.get(fontIndex);
        if (t == null && !addedFontIndices.contains(fontIndex)) {
            int unicodeStart = fontIndex * 256;
            String var10000;
            switch (unicodeStart) {
                case 0 -> {
                    if (SGA_font != null) {
                        return SGA_font;
                    } else {
                        return SGA_font = createFontTexturev2(unicodeStart, SGA_fileName);
                    }
                }
                case 256 -> var10000 = "cosmic-reach-font-0100-extended-A.png";
                case 512 -> var10000 = "cosmic-reach-font-0200.png";
                case 768 -> var10000 = "cosmic-reach-font-0300-diacritics-greek-coptic.png";
                case 1024 -> var10000 = "cosmic-reach-font-0400-cyrillic.png";
                case 12288 -> var10000 = "cosmic-reach-font-3000-kana.png";
                default -> var10000 = "cosmic-reach-font-" + Integer.toHexString(unicodeStart).toUpperCase() + ".png";
            }

            String fontName = var10000;
            createFontTexture(unicodeStart, fontName);
        }

        return t;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void clinit(CallbackInfo ci) {
        allFontTextures.clear();
        addedFontIndices.clear();
        createFontTexturev2(0, "standard_galactic_alphabet.png");
    }

}
