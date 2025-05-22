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
import java.nio.file.Files;
import java.time.LocalDate;

public class PuzzleConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger("Puzzle | Config");
    public static JsonObject puzzleConfig = new JsonObject();
    public static String puzzleConfigFileName = "/PuzzleSettings.json";

    public static void loadPuzzleConfig(){
        LOGGER.info("loading puzzle config");
        File file = new File(SaveLocation.getSaveFolderLocation() + puzzleConfigFileName);
        if (file.exists()){
            File RelativeFile = new File(SaveLocation.getSaveFolderLocation());
            RawAssetLoader.RawFileHandle config = RawAssetLoader.getLowLevelRelativeAsset(RelativeFile, puzzleConfigFileName);
            try {
                puzzleConfig = JsonValue.readHjson(config.getString()).asObject();
            } catch (RuntimeException e){
                LOGGER.error("Couldn't read hjson in the puzzle config file, using internal default | exception info: {}", e.toString());
                config.dispose();
                loadDefaultPuzzleConfig();
            }
            config.dispose();
        } else {
            loadDefaultPuzzleConfig();
        }
    }

    public static void loadDefaultPuzzleConfig(){
        LOGGER.info("loading default puzzle config");
        puzzleConfig = getDefaultJson();
        savePuzzleConfig();
    }

    public static void savePuzzleConfig(){
        File file = new File(SaveLocation.getSaveFolderLocation() + puzzleConfigFileName);
        try {
            FileWriter writer = new FileWriter(file);
            if(!file.exists())
                Files.createFile(file.toPath());
            writer.write(puzzleConfig.toString(Stringify.FORMATTED));
        } catch (IOException e){
            LOGGER.error("IO exception with '{}' ",file.getAbsoluteFile());
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
