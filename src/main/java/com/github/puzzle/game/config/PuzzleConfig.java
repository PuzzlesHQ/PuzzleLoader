package com.github.puzzle.game.config;

import com.github.puzzle.core.loader.util.RawAssetLoader;
import finalforeach.cosmicreach.io.SaveLocation;
import org.checkerframework.checker.index.qual.UpperBoundUnknown;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hjson.Stringify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class PuzzleConfig {
    public static final Logger PUZZLE_CONFIG = LoggerFactory.getLogger("Puzzle | Config");
    public static JsonObject puzzleConfig;
    public static String puzzleConfigFileName = "/PuzzleSettings.json";

    public static void loadPuzzleConfig(){
        PUZZLE_CONFIG.info("loading puzzle config");
        File file = new File(SaveLocation.getSaveFolderLocation() + puzzleConfigFileName);
        if (file.exists()){
            File RelativeFile = new File(SaveLocation.getSaveFolderLocation());
            RawAssetLoader.RawFileHandle config = RawAssetLoader.getLowLevelRelativeAsset(RelativeFile, puzzleConfigFileName);
            puzzleConfig = JsonValue.readHjson(config.getString()).asObject();
            config.dispose();
            if (puzzleConfig == null){
                loadDefaultPuzzleConfig();
            }
        } else {
            loadDefaultPuzzleConfig();
        }
    }

    public static void loadDefaultPuzzleConfig(){
        PUZZLE_CONFIG.info("loading default puzzle config");
        puzzleConfig = getDefaultJson();
        savePuzzleConfig();
    }

    public static void savePuzzleConfig(){
        File file = new File(SaveLocation.getSaveFolderLocation() + puzzleConfigFileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(puzzleConfig.toString(Stringify.FORMATTED));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JsonObject getDefaultJson(){
        return new JsonObject()
                .add("isAprilFools", false);
    }

    public static boolean isAprilFools(){
        LocalDate today = LocalDate.now();
        if (today.getMonthValue() == 4 && today.getDayOfMonth() == 1) {
            return true;
        } else {
            return PuzzleConfig.getBoolean("isAprilFools", false);
        }
    }

    public static boolean getBoolean(String name, boolean defaultValue) {
        return puzzleConfig.getBoolean(name, defaultValue);
    }

}
