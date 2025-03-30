package com.github.puzzle.game.engine.rendering.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.ui.FontRenderer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.puzzle.game.engine.rendering.text.FormatColors.FORMAT_KEY;

public class FormatText {
    public static final Pattern FORMAT_PATTER = Pattern.compile(FORMAT_KEY + "(\\[([0-9A-Fa-f]{6})\\]|[0-9A-FRa-fr]{1})");
    public static final Pattern FORMAT_VALUE = Pattern.compile("(?i)("+FORMAT_KEY+"\\[[0-9A-FR]{6}][^"+FORMAT_KEY+"]+|"+FORMAT_KEY+"[0-9A-FR][^"+FORMAT_KEY+"]+)");

    final String text;
    final String rawText;
    final List<TextPart> parts;

    FormatText(@NonNull String text, @NonNull List<TextPart> parts) {
        String text1;
        text1 = text;
        for (MatchResult r : FORMAT_PATTER.matcher(text).results().toList()) {
            text1 = text1.replaceAll(r.group().replace("[", "\\["), "");
        }
//        this.text = FORMAT_PATTER.matcher(text).replaceAll("");
        this.text = text1;
        this.rawText = text;
        this.parts = parts;
    }

    public static FormatText of(String text) {
        return of(text, Color.WHITE.cpy());
    }

    public static FormatText of(String text, Color resetColor) {
        List<TextPart> parts = new ArrayList<>();

        // Repair String if it's encoded in a broken charset
        if (text == null)
            return new FormatText("", new ArrayList<>());

        text = new String(text.getBytes());
        if (!text.startsWith(FORMAT_KEY))
            text = FORMAT_KEY + "r" + text;

        if (text.contains(FORMAT_KEY)) {
            List<MatchResult> resultList = FORMAT_VALUE.matcher(text).results().toList();

            for (MatchResult result : resultList) {
                char[] chars = new char[result.end() - result.start()];
                text.getChars(result.start(), result.end(), chars, 0);

                String segment = new String(chars);
                Matcher m = FORMAT_PATTER.matcher(segment);

                String colourFormatter = "§r";
                if (m.find()) {
                    colourFormatter = m.group();
                }

                String segmentText = segment.replaceAll(colourFormatter.replace("[", "\\["), "");

                parts.add(new TextPart(segmentText, FormatColors.toColor(colourFormatter, resetColor)));
            }
        }

        return new FormatText(text, parts);
    }

    public String getText() {
        return text;
    }

    public String getRawText() {
        return rawText;
    }

    public List<TextPart> getParts() {
        return parts;
    }

    public String strip() {
        return getText().strip();
    }

    @Override
    public String toString() {
        return getText();
    }

    public record TextPart(String text, Color color) {
        public Vector2 getDimensions(Viewport vp) {
            return FontRenderer.getTextDimensions(vp, text, new Vector2());
        }

        public float getWidth(Viewport vp) {
            return getDimensions(vp).x;
        }

        public float getHeight(Viewport vp) {
            return getDimensions(vp).y;
        }
    }

}
